package com.birberber.domain.aaaaaaaaaaaaaaaa;

public class OrnekRelations {

// https://github.com/kriscfoster/Spring-Data-JPA-Relationships/tree/master/src/main/java/com/kriscfoster/school

//--OneToOne--

//    Person.class
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
//    private Address address;

//    --------------------------------

//    Address.class
//    @OneToOne(mappedBy = "address")
//    private Person person;

//--OneToMany--

//    Teacher.class
//    @OneToMany(mappedBy = "teacher")
//    private Set<Subject> subjects;

//----------------------------------------

//    Subject.class
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    private Teacher teacher;


//        --ManyToMany--

//    @ManyToMany
//    @JoinTable(
//            name = "student_enrolled",
//            joinColumns = @JoinColumn(name = "subject_id"),
//            inverseJoinColumns = @JoinColumn(name = "student_id")
//    )
//    Set<Student> enrolledStudents = new HashSet<>();
//
//    @ManyToMany(mappedBy = "enrolledStudents")
//    private Set<Subject> subjects = new HashSet<>();

}
