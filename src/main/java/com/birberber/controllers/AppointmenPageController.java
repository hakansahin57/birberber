package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.product.Product;
import com.birberber.domain.store.Store;
import com.birberber.dto.AppointmentSummaryDto;
import com.birberber.forms.BookingForm;
import com.birberber.repositories.ProductRepository;
import com.birberber.services.appointment.AppointmentService;
import com.birberber.services.store.NearbyStoreService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appointments")
public class AppointmenPageController {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private final AppointmentService appointmentService;
    private final NearbyStoreService nearbyStoreService;
    private final ProductRepository productRepository;
    private final MessageSource messageSource;

    public AppointmenPageController(
            AppointmentService appointmentService,
            NearbyStoreService nearbyStoreService,
            ProductRepository productRepository,
            MessageSource messageSource) {
        this.appointmentService = appointmentService;
        this.nearbyStoreService = nearbyStoreService;
        this.productRepository = productRepository;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getAllAppointments(@AuthenticationPrincipal UserDetails user, Model model) {
        if (user != null) {
            List<AppointmentSummaryDto> summaries = appointmentService.getAppointmentsForUser(user.getUsername())
                    .stream()
                    .map(AppointmentSummaryDto::from)
                    .collect(Collectors.toList());
            model.addAttribute("appointments", summaries);
        }
        return Constants.APPOINTMENTS_PAGE;
    }

    @GetMapping("/checkout")
    public String checkout(
            @RequestParam(required = false) Long appointmentId,
            @ModelAttribute BookingForm bookingForm,
            Model model) {
        if (appointmentId != null) {
            Appointment appointment = appointmentService.findById(appointmentId);
            bookingForm.setStoreId(appointment.getStore().getId());
            bookingForm.setServiceId(appointment.getService().getId());
            bookingForm.setDate(appointment.getStartTime().toLocalDate().toString());
            bookingForm.setStartTime(appointment.getStartTime().toLocalTime().format(TIME_FORMAT));
            model.addAttribute("appointment", appointment);
            populateCheckoutModel(model, appointment.getStore(), appointment.getService(), bookingForm);
            return Constants.CHECKOUT_PAGE;
        }

        if (bookingForm.getStoreId() == null || bookingForm.getServiceId() == null || bookingForm.getStartTime() == null) {
            return "redirect:/stores";
        }
        Store store = nearbyStoreService.findById(bookingForm.getStoreId());
        Product service = productRepository.findById(bookingForm.getServiceId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));
        populateCheckoutModel(model, store, service, bookingForm);
        return Constants.CHECKOUT_PAGE;
    }

    @PostMapping("/pay")
    public String pay(
            @AuthenticationPrincipal UserDetails user,
            @ModelAttribute BookingForm bookingForm,
            @RequestParam(required = false) Long appointmentId,
            RedirectAttributes redirectAttributes) {
        try {
            Long id = appointmentId;
            if (id == null) {
                String startDateTime = bookingForm.getDate() + "T" + bookingForm.getStartTime();
                Appointment appointment = appointmentService.createPendingAppointment(
                        user.getUsername(),
                        bookingForm.getStoreId(),
                        bookingForm.getServiceId(),
                        startDateTime
                );
                id = appointment.getId();
            }
            Appointment confirmed = appointmentService.completePayment(id, bookingForm.getCardNumber());
            Locale locale = LocaleContextHolder.getLocale();
            redirectAttributes.addFlashAttribute("successMsg",
                    messageSource.getMessage("appointment.flash.confirmed",
                            new Object[]{
                                    confirmed.getStore().getName(),
                                    confirmed.getStartTime().format(DISPLAY_FORMAT)
                            },
                            locale));
            return "redirect:/appointments";
        } catch (IllegalStateException | IllegalArgumentException ex) {
            Locale locale = LocaleContextHolder.getLocale();
            redirectAttributes.addFlashAttribute("errorMsg",
                    messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale));
            return "redirect:/stores/" + bookingForm.getStoreId();
        }
    }

    private void populateCheckoutModel(Model model, Store store, Product service, BookingForm bookingForm) {
        model.addAttribute("store", store);
        model.addAttribute("service", service);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("slotLabel", formatSlot(bookingForm.getDate(), bookingForm.getStartTime(), service.getDurationMinutes()));
    }

    private String formatSlot(String date, String startTime, int durationMinutes) {
        LocalDateTime start = LocalDateTime.parse(date + "T" + startTime, DATE_TIME_FORMAT);
        LocalDateTime end = start.plusMinutes(durationMinutes);
        return start.format(DISPLAY_FORMAT) + " - " + end.format(TIME_FORMAT);
    }
}
