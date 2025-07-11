package com.tests.test_case_helper.entity;

import com.tests.test_case_helper.enums.Environment;
import com.tests.test_case_helper.enums.TestSuiteRunStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "test_suite_run_session")
public class TestSuiteRunSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_suite_id")
    private TestSuite testSuite;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User executedBy;

    @Column(name = "environment")
    @Enumerated(EnumType.STRING)
    private Environment environment;

    @OneToMany(mappedBy = "testSuiteRunSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TestCaseRunResult> testCaseRunResults;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TestSuiteRunStatus status;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestSuiteRunSession that = (TestSuiteRunSession) o;
        return Objects.equals(id, that.id)
                && Objects.equals(testSuite, that.testSuite)
                && Objects.equals(startTime, that.startTime)
                && Objects.equals(endTime, that.endTime)
                && Objects.equals(executedBy, that.executedBy)
                && environment == that.environment
                && Objects.equals(testCaseRunResults, that.testCaseRunResults)
                && status == that.status
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                testSuite,
                startTime,
                endTime,
                executedBy,
                environment,
                testCaseRunResults,
                status,
                createdAt,
                updatedAt
        );
    }
}
