package se.kth.deptrim;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

  /**
   * Create the test case.
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
  }

  /**
   * The test suite.
   *
   * @return the suite of tests being tested.
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * Rigorous test.
   */
  public void testApp() {
    assertTrue(true);
  }
}