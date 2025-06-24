package com.tests.test_case_helper.entity;

import com.tests.test_case_helper.entity.cases.TestCase;
import com.tests.test_case_helper.enums.TestCaseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test_case_run_result")
public class TestCaseRunResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_suite_run_session_id")
    private TestSuiteRunSession testSuiteRunSession;

    @ManyToOne
    @JoinColumn(name = "test_case_id")
    private TestCase testCase;

    @Column(name = "actual_result")
    private String actualResult;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TestCaseStatus status;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
