package com.tests.test_case_helper.entity;

import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.enums.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_suite")
public class TestSuite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "tag")
    @Enumerated(EnumType.STRING)
    private Tag tag;

    @OneToMany(mappedBy = "testsuite", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TestCase> testsCases;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updated_at;
}
