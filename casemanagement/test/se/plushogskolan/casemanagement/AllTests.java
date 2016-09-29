package se.plushogskolan.casemanagement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import se.plushogskolan.casemanagement.model.TestIssue;
import se.plushogskolan.casemanagement.model.TestTeam;
import se.plushogskolan.casemanagement.model.TestUser;
import se.plushogskolan.casemanagement.model.TestWorkItem;

@RunWith(Suite.class)

@Suite.SuiteClasses({ TestIssue.class, TestTeam.class, TestUser.class, TestWorkItem.class })

public class AllTests {
}