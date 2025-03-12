package com.tests.test_case_helper.entity.cases;

import com.tests.test_case_helper.entity.TestSuite;
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
@Table(name = "test_case")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_case_id")
    private List<TestCasePrecondition> testCasePrecondition;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_case_id")
    private List<TestCaseData> testCaseData;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_case_id")
    private List<TestCaseStep> steps;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_case_id")
    private List<TestCaseExpectedResult> expectedResult;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "test_suit_id")
    private TestSuite testSuite;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDate updated_at;
}
