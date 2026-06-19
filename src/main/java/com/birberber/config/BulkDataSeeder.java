package com.birberber.config;

import com.birberber.domain.address.Address;
import com.birberber.domain.appointment.Appointment;
import com.birberber.domain.appointment.AppointmentStatus;
import com.birberber.domain.payment.Payment;
import com.birberber.domain.payment.PaymentStatus;
import com.birberber.domain.price.Currency;
import com.birberber.domain.price.Price;
import com.birberber.domain.product.Product;
import com.birberber.domain.store.Store;
import com.birberber.domain.store.StorePhoto;
import com.birberber.domain.store.StoreReview;
import com.birberber.domain.store.StoreType;
import com.birberber.domain.store.WorkingHour;
import com.birberber.domain.user.Admin;
import com.birberber.domain.user.Employee;
import com.birberber.domain.user.User;
import com.birberber.repositories.AppointmentRepository;
import com.birberber.repositories.StorePhotoRepository;
import com.birberber.repositories.StoreRepository;
import com.birberber.repositories.StoreReviewRepository;
import com.birberber.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class BulkDataSeeder {

    private static final Logger log = LoggerFactory.getLogger(BulkDataSeeder.class);

    static final String DEMO_EMAIL = "hakan@mail.com";
    static final String ADMIN_EMAIL = "admin@mail.com";
    static final String ADMIN_PASSWORD = "123";
    static final int TARGET_STORE_COUNT = 1008;
    static final int TARGET_CUSTOMER_COUNT = 100;
    static final int TARGET_EMPLOYEE_COUNT = 100;
    static final int TARGET_APPOINTMENT_COUNT = 100;
    private static final int NAME_MAX_LENGTH = 30;

    private static String truncateName(String value) {
        if (value == null) {
            return null;
        }
        return value.length() <= NAME_MAX_LENGTH ? value : value.substring(0, NAME_MAX_LENGTH);
    }

    private static String bulkStoreName(StoreType type, String district, int index) {
        String prefix = type == StoreType.HAIRDRESSER ? "K" : "B";
        return truncateName(prefix + " " + district + " " + index);
    }

    private static final String[] DISTRICTS = {
            "Kadıköy", "Beşiktaş", "Şişli", "Üsküdar", "Bakırköy", "Ataşehir", "Maltepe",
            "Beyoğlu", "Fatih", "Sarıyer", "Kartal", "Pendik", "Ümraniye", "Bağcılar",
            "Esenyurt", "Beylikdüzü", "Sultangazi", "Zeytinburnu", "Kağıthane", "Güngören"
    };

    private static final String[] BARBER_SERVICES = {"Saç Kesimi", "Sakal Tıraşı", "Çocuk Tıraşı"};
    private static final String[] HAIR_SERVICES = {"Saç Kesimi", "Fön", "Boyama"};
    private static final String[] FIRST_NAMES = {
            "Ahmet", "Mehmet", "Ali", "Can", "Emre", "Burak", "Serkan", "Oğuz", "Kemal", "Murat",
            "Ayşe", "Fatma", "Zeynep", "Elif", "Deniz", "Selin", "Cem", "Barış", "Tolga", "Onur"
    };
    private static final String[] LAST_NAMES = {
            "Yılmaz", "Kaya", "Demir", "Şahin", "Çelik", "Yıldız", "Aydın", "Öztürk", "Arslan", "Koç"
    };

    private static final String[] REVIEW_COMMENTS = {
            "Çok temiz ve profesyonel bir ortam.",
            "Usta işi tıraş, kesinlikle tavsiye ederim.",
            "Randevu sistemi çok pratik, beklemeden girdim.",
            "Fiyat/performans çok iyi.",
            "Sakal tıraşı mükemmeldi.",
            "Personel çok ilgili ve güler yüzlü.",
            "Modern dükkan, hijyen on numara.",
            "Her seferinde aynı kalite, teşekkürler."
    };

    private static final String[] PHOTO_SEEDS = {"barber1", "barber2", "barber3", "salon1", "salon2"};

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final StorePhotoRepository storePhotoRepository;
    private final StoreReviewRepository storeReviewRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Random random = new Random(42);

    public BulkDataSeeder(
            StoreRepository storeRepository,
            UserRepository userRepository,
            AppointmentRepository appointmentRepository,
            StorePhotoRepository storePhotoRepository,
            StoreReviewRepository storeReviewRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.storePhotoRepository = storePhotoRepository;
        this.storeReviewRepository = storeReviewRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void run() {
        seedCoreUsersIfNeeded();
        seedNamedStoresIfNeeded();
        seedBulkStoresIfNeeded();
        seedBulkCustomersIfNeeded();
        seedBulkEmployeesIfNeeded();
        seedCoreAppointmentsIfNeeded();
        seedBulkAppointmentsIfNeeded();
        seedStoreProfilesIfNeeded();
    }

    private void seedStoreProfilesIfNeeded() {
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            return;
        }
        List<User> reviewers = collectReviewers();
        if (reviewers.isEmpty()) {
            return;
        }

        int photosAdded = 0;
        int reviewsAdded = 0;
        List<StorePhoto> photoBatch = new ArrayList<>();
        List<StoreReview> reviewBatch = new ArrayList<>();
        for (Store store : stores) {
            Long storeId = store.getId();
            if (!storePhotoRepository.existsByStoreId(storeId)) {
                photoBatch.addAll(buildPhotosForStore(store));
                photosAdded += 3;
                if (photoBatch.size() >= 150) {
                    storePhotoRepository.saveAll(photoBatch);
                    photoBatch.clear();
                }
            }
            if (!storeReviewRepository.existsByStoreId(storeId)) {
                int count = 2 + random.nextInt(4);
                reviewBatch.addAll(buildReviewsForStore(store, reviewers, count));
                reviewsAdded += count;
                if (reviewBatch.size() >= 100) {
                    storeReviewRepository.saveAll(reviewBatch);
                    reviewBatch.clear();
                }
            }
        }
        if (!photoBatch.isEmpty()) {
            storePhotoRepository.saveAll(photoBatch);
        }
        if (!reviewBatch.isEmpty()) {
            storeReviewRepository.saveAll(reviewBatch);
        }
        if (photosAdded > 0 || reviewsAdded > 0) {
            log.info("Berber profilleri güncellendi: {} fotoğraf, {} yorum", photosAdded, reviewsAdded);
        }
    }

    private List<User> collectReviewers() {
        List<User> reviewers = new ArrayList<>();
        User hakan = userRepository.findByEmail(DEMO_EMAIL);
        if (hakan != null) {
            reviewers.add(hakan);
        }
        User demo = userRepository.findByEmail("demo@mail.com");
        if (demo != null) {
            reviewers.add(demo);
        }
        User ayse = userRepository.findByEmail("ayse@mail.com");
        if (ayse != null) {
            reviewers.add(ayse);
        }
        for (int i = 1; i <= TARGET_CUSTOMER_COUNT; i++) {
            User u = userRepository.findByEmail("musteri" + i + "@birberber.test");
            if (u != null) {
                reviewers.add(u);
            }
        }
        return reviewers;
    }

    private List<StorePhoto> buildPhotosForStore(Store store) {
        List<StorePhoto> photos = new ArrayList<>();
        for (int p = 0; p < 3; p++) {
            StorePhoto photo = new StorePhoto();
            photo.setName(truncateName("Foto " + (p + 1)));
            photo.setStore(store);
            String seed = PHOTO_SEEDS[(int) ((store.getId() + p) % PHOTO_SEEDS.length)];
            photo.setUrl("https://picsum.photos/seed/" + seed + store.getId() + p + "/800/500");
            photo.setDisplayOrder(p);
            photos.add(photo);
        }
        return photos;
    }

    private List<StoreReview> buildReviewsForStore(Store store, List<User> reviewers, int reviewCount) {
        List<StoreReview> reviews = new ArrayList<>();
        for (int r = 0; r < reviewCount; r++) {
            StoreReview review = new StoreReview();
            review.setName(truncateName("Yorum " + (r + 1)));
            review.setStore(store);
            review.setCustomer(reviewers.get((int) ((store.getId() + r) % reviewers.size())));
            review.setRating(3 + random.nextInt(3));
            review.setComment(REVIEW_COMMENTS[(int) ((store.getId() + r) % REVIEW_COMMENTS.length)]);
            reviews.add(review);
        }
        return reviews;
    }

    static void logAdminCredentials() {
        log.info("""
                
                ========================================
                  Admin Giriş Bilgileri
                  Kullanıcı adı: {}
                  Şifre: {}
                ========================================
                """, ADMIN_EMAIL, ADMIN_PASSWORD);
    }

    private void seedCoreUsersIfNeeded() {
        if (userRepository.findByEmail(DEMO_EMAIL) != null) {
            return;
        }
        String encoded = passwordEncoder.encode(ADMIN_PASSWORD);

        userRepository.save(createUser("Hakan", "Şahin", DEMO_EMAIL, "5551234567", encoded, "ROLE_USER"));
        userRepository.save(createUser("Demo", "Kullanıcı", "demo@mail.com", "5559876543", encoded, "ROLE_USER"));
        userRepository.save(createUser("Ayşe", "Yılmaz", "ayse@mail.com", "5551112233", encoded, "ROLE_USER"));

        Admin admin = new Admin();
        admin.setName("Admin");
        admin.setLastName("BirBerber");
        admin.setEmail(ADMIN_EMAIL);
        admin.setPhoneNumber("5550000000");
        admin.setPassword(encoded);
        admin.setRole("ROLE_ADMIN");
        admin.setAsd("admin");
        userRepository.save(admin);
    }

    private void seedNamedStoresIfNeeded() {
        if (storeRepository.count() > 0) {
            return;
        }
        saveStore("Kadıköy Berber", StoreType.BARBER, "Caferağa Mah. Moda Cad. No:12", 40.9903, 29.0257, "Kadıköy", "+90 216 555 0101", BARBER_SERVICES);
        saveStore("Beşiktaş Kuaför", StoreType.HAIRDRESSER, "Abbasağa Mah. Beşiktaş Cad. No:45", 41.0422, 29.0067, "Beşiktaş", "+90 212 555 0202", HAIR_SERVICES);
        saveStore("Şişli Berber Evi", StoreType.BARBER, "Halaskargazi Cad. No:88", 41.0602, 28.9877, "Şişli", "+90 212 555 0303", BARBER_SERVICES);
        saveStore("Üsküdar Berber", StoreType.BARBER, "Mimar Sinan Mah. No:3", 41.0254, 29.0158, "Üsküdar", "+90 216 555 0404", BARBER_SERVICES);
        saveStore("Bakırköy Berber", StoreType.BARBER, "Ataköy Mah. No:21", 40.9780, 28.8772, "Bakırköy", "+90 212 555 0505", BARBER_SERVICES);
        saveStore("Ataşehir Kuaför", StoreType.HAIRDRESSER, "Ataşehir Bulvarı No:15", 40.9923, 29.1244, "Ataşehir", "+90 216 555 0606", HAIR_SERVICES);
        saveStore("Maltepe Berber", StoreType.BARBER, "Bağlarbaşı Mah. No:7", 40.9351, 29.1512, "Maltepe", "+90 216 555 0707", BARBER_SERVICES);
        saveStore("Taksim Berber", StoreType.BARBER, "İstiklal Cad. No:102", 41.0340, 28.9770, "Beyoğlu", "+90 212 555 0808", BARBER_SERVICES);
    }

    private void seedBulkStoresIfNeeded() {
        long current = storeRepository.count();
        int toCreate = (int) (TARGET_STORE_COUNT - current);
        if (toCreate <= 0) {
            return;
        }
        log.info("Toplu berber verisi ekleniyor: {} adet (hedef: {})", toCreate, TARGET_STORE_COUNT);

        List<Store> batch = new ArrayList<>();
        int startIndex = (int) current + 1;
        for (int i = 0; i < toCreate; i++) {
            int index = startIndex + i;
            String district = DISTRICTS[i % DISTRICTS.length];
            boolean hairdresser = index % 3 == 0;
            StoreType type = hairdresser ? StoreType.HAIRDRESSER : StoreType.BARBER;
            String name = bulkStoreName(type, district, index);
            double lat = 40.88 + random.nextDouble() * 0.38;
            double lng = 28.78 + random.nextDouble() * 0.52;
            String line1 = district + " Mah. No:" + (index % 200 + 1) + ", İstanbul";
            String phone = String.format("+905%09d", 5000000000L + index);
            String[] services = hairdresser ? HAIR_SERVICES : BARBER_SERVICES;
            batch.add(buildStore(name, type, line1, lat, lng, district, phone, services, index));

            if (batch.size() >= 50) {
                storeRepository.saveAll(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            storeRepository.saveAll(batch);
        }
        log.info("Berber verisi tamamlandı. Toplam: {}", storeRepository.count());
    }

    private void seedBulkCustomersIfNeeded() {
        if (userRepository.findByEmail("musteri1@birberber.test") != null) {
            return;
        }
        log.info("{} adet müşteri kullanıcısı ekleniyor", TARGET_CUSTOMER_COUNT);
        String encoded = passwordEncoder.encode("123");
        List<User> batch = new ArrayList<>();
        for (int i = 1; i <= TARGET_CUSTOMER_COUNT; i++) {
            String first = FIRST_NAMES[i % FIRST_NAMES.length];
            String last = LAST_NAMES[i % LAST_NAMES.length];
            batch.add(createUser(first, last, "musteri" + i + "@birberber.test",
                    String.format("555%07d", 1000000 + i), encoded, "ROLE_USER"));
            if (batch.size() >= 25) {
                userRepository.saveAll(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            userRepository.saveAll(batch);
        }
    }

    private void seedBulkEmployeesIfNeeded() {
        if (userRepository.findByEmail("calisan1@birberber.test") != null) {
            return;
        }
        log.info("{} adet çalışan ekleniyor", TARGET_EMPLOYEE_COUNT);
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            return;
        }
        String encoded = passwordEncoder.encode("123");
        List<User> batch = new ArrayList<>();
        for (int i = 1; i <= TARGET_EMPLOYEE_COUNT; i++) {
            Employee employee = new Employee();
            employee.setName(truncateName(FIRST_NAMES[(i + 3) % FIRST_NAMES.length]));
            employee.setLastName(LAST_NAMES[(i + 5) % LAST_NAMES.length]);
            employee.setEmail("calisan" + i + "@birberber.test");
            employee.setPhoneNumber(String.format("554%07d", 2000000 + i));
            employee.setPassword(encoded);
            employee.setRole("ROLE_USER");
            employee.setStore(stores.get(i % stores.size()));
            batch.add(employee);
            if (batch.size() >= 25) {
                userRepository.saveAll(batch);
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            userRepository.saveAll(batch);
        }
    }

    private void seedCoreAppointmentsIfNeeded() {
        if (appointmentRepository.count() > 0) {
            return;
        }
        User hakan = userRepository.findByEmail(DEMO_EMAIL);
        User demo = userRepository.findByEmail("demo@mail.com");
        User ayse = userRepository.findByEmail("ayse@mail.com");
        List<Store> stores = storeRepository.findAll();
        if (hakan == null || stores.size() < 5) {
            return;
        }

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        saveAppointment(hakan, stores.get(0), LocalDate.now().minusDays(3), LocalTime.of(14, 0), 0, AppointmentStatus.CONFIRMED);
        saveAppointment(hakan, stores.get(0), tomorrow, LocalTime.of(15, 0), 0, AppointmentStatus.CONFIRMED);
        saveAppointment(hakan, stores.get(1), LocalDate.now().plusDays(7), LocalTime.of(11, 0), 0, AppointmentStatus.CONFIRMED);
        if (demo != null) {
            saveAppointment(demo, stores.get(0), tomorrow, LocalTime.of(10, 0), 1, AppointmentStatus.CONFIRMED);
            saveAppointment(demo, stores.get(Math.min(3, stores.size() - 1)), tomorrow, LocalTime.of(16, 0), 0, AppointmentStatus.CONFIRMED);
        }
        if (ayse != null) {
            saveAppointment(ayse, stores.get(Math.min(2, stores.size() - 1)), tomorrow, LocalTime.of(15, 0), 0, AppointmentStatus.CONFIRMED);
            saveAppointment(ayse, stores.get(Math.min(4, stores.size() - 1)), tomorrow, LocalTime.of(13, 0), 1, AppointmentStatus.CONFIRMED);
        }
    }

    private void seedBulkAppointmentsIfNeeded() {
        long current = appointmentRepository.count();
        int toCreate = (int) (TARGET_APPOINTMENT_COUNT - current);
        if (toCreate <= 0) {
            return;
        }
        log.info("{} adet randevu ekleniyor (hedef: {})", toCreate, TARGET_APPOINTMENT_COUNT);

        List<User> customers = new ArrayList<>();
        User hakan = userRepository.findByEmail(DEMO_EMAIL);
        if (hakan != null) {
            customers.add(hakan);
        }
        for (int i = 1; i <= TARGET_CUSTOMER_COUNT; i++) {
            User u = userRepository.findByEmail("musteri" + i + "@birberber.test");
            if (u != null) {
                customers.add(u);
            }
        }
        List<Store> stores = storeRepository.findAll();
        if (customers.isEmpty() || stores.isEmpty()) {
            return;
        }

        for (int i = 0; i < toCreate; i++) {
            User customer = customers.get(i % customers.size());
            Store store = stores.get((i * 7 + 13) % stores.size());
            int dayOffset = (i % 14) + 1;
            int hour = 9 + (i % 9);
            int minute = (i % 2) * 30;
            AppointmentStatus status = i % 5 == 0 ? AppointmentStatus.PENDING_PAYMENT : AppointmentStatus.CONFIRMED;
            saveAppointment(customer, store, LocalDate.now().plusDays(dayOffset), LocalTime.of(hour, minute), i % 3, status);
        }
    }

    private void saveStore(String name, StoreType type, String line1, double lat, double lng, String district, String phone, String[] services) {
        storeRepository.save(buildStore(truncateName(name), type, line1, lat, lng, district, phone, services, null));
    }

    private Store buildStore(String name, StoreType type, String line1, double lat, double lng, String district, String phone, String[] services, Integer index) {
        Store store = new Store();
        store.setName(truncateName(name));
        store.setStoreType(type);
        store.setPhoneNumber(phone);

        Address address = new Address();
        String addressName = index != null ? "Adres " + index : truncateName(name + " Adres");
        address.setName(truncateName(addressName));
        address.setLine1(line1);
        address.setLine2(district);
        address.setLatitude(lat);
        address.setLongitude(lng);
        store.setAddress(address);

        Currency currency = new Currency();
        currency.setName("TRY");
        currency.setIsocode("TRY");

        List<Product> products = new ArrayList<>();
        for (int s = 0; s < services.length; s++) {
            Product product = new Product();
            product.setName(truncateName(services[s]));
            product.setDurationMinutes(30 + (s * 15) + random.nextInt(15));
            product.setStore(store);

            Price price = new Price();
            price.setName(truncateName(services[s] + " Fiyat"));
            price.setValue(100 + random.nextInt(200));
            price.setCurrency(currency);
            product.setPrice(price);
            products.add(product);
        }
        store.setServices(products);
        store.setWorkingHours(createWorkingHours(store));
        return store;
    }

    private List<WorkingHour> createWorkingHours(Store store) {
        List<WorkingHour> hours = new ArrayList<>();
        for (String day : List.of("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")) {
            WorkingHour hour = new WorkingHour();
            hour.setName(day);
            hour.setDay(day);
            hour.setOpeningHour("09:00");
            hour.setClosingHour("20:00");
            hour.setStore(store);
            hours.add(hour);
        }
        WorkingHour sunday = new WorkingHour();
        sunday.setName("SUNDAY");
        sunday.setDay("SUNDAY");
        sunday.setOpeningHour("10:00");
        sunday.setClosingHour("18:00");
        sunday.setStore(store);
        hours.add(sunday);
        return hours;
    }

    private void saveAppointment(User customer, Store store, LocalDate date, LocalTime start, int serviceIndex, AppointmentStatus status) {
        store = storeRepository.findById(store.getId()).orElse(store);
        List<Product> services = store.getServices();
        if (services == null || services.isEmpty()) {
            return;
        }
        Product service = services.get(Math.min(serviceIndex, services.size() - 1));
        LocalDateTime startTime = date.atTime(start);
        LocalDateTime endTime = startTime.plusMinutes(service.getDurationMinutes());

        Appointment appointment = new Appointment();
        appointment.setName(truncateName(service.getName() + " - " + store.getName()));
        appointment.setCustomer(customer);
        appointment.setStore(store);
        appointment.setService(service);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(status);

        if (status == AppointmentStatus.CONFIRMED || status == AppointmentStatus.PENDING_PAYMENT) {
            Payment payment = new Payment();
            payment.setName(truncateName("Ödeme - " + service.getName()));
            payment.setAmount(service.getPrice() != null ? service.getPrice().getValue() : 0);
            payment.setStatus(status == AppointmentStatus.CONFIRMED ? PaymentStatus.COMPLETED : PaymentStatus.PENDING);
            payment.setTransactionId("TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            payment.setCardLastFour("4242");
            payment.setAppointment(appointment);
            appointment.setPayment(payment);
        }

        appointmentRepository.save(appointment);
    }

    private User createUser(String first, String last, String email, String phone, String encoded, String role) {
        User user = new User();
        user.setName(truncateName(first));
        user.setLastName(last);
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(encoded);
        user.setRole(role);
        return user;
    }
}
