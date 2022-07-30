package com.birberber.domain.address;

import com.birberber.domain.item.Item;

import javax.persistence.*;
import java.util.List;

@Entity
public class District extends Item {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToMany(mappedBy = "district")
    private List<Neighborhood> neighborhoods;


    //--OneToMany--

//    Teacher.class
//    @OneToMany(mappedBy = "teacher")
//    private Set<Subject> subjects;

//----------------------------------------

//    Subject.class
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    private Teacher teacher;
}
