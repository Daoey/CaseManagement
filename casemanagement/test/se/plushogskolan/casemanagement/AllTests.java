package se.plushogskolan.casemanagement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import se.plushogskolan.casemanagement.model.TestIssue;
import se.plushogskolan.casemanagement.model.TestTeam;
import se.plushogskolan.casemanagement.model.TestUser;
import se.plushogskolan.casemanagement.model.TestWorkItem;
import se.plushogskolan.casemanagement.repository.sql.TestSqlIssueRepository;
import se.plushogskolan.casemanagement.repository.sql.TestSqlTeamRepository;

@RunWith(Suite.class)

@Suite.SuiteClasses({ TestIssue.class, TestTeam.class, TestUser.class, TestWorkItem.class, TestSqlTeamRepository.class,
        TestSqlIssueRepository.class })

public class AllTests {
}