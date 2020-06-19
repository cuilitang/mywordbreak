package cui.litang.test;

import static org.junit.Assert.assertArrayEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

	App app;
	public String CONS_DIC = "{i,like,sam,sung,samsung,mobile,ice,cream,man go,and}";
	public String CONS_CUST_DIC = "{i,like,sam,sung,mobile,icecream,man go,mango,and}";
	public String CONS_INPUT = "ilikeicecreamandmango";

	@Before
	public void before() {
		app = new App();
	}

	@After
	public void after() {
		app = null;
	}

	@Test
	public void testStage1() {

		String stage1 = app.stage1(CONS_INPUT, CONS_DIC);
		byte[] expected = stage1.getBytes();
		byte[] actual = "i like ice cream and man go".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

	@Test
	public void testStage2() {

		String stage2 = app.stage2(CONS_INPUT, CONS_CUST_DIC);

		byte[] expected = stage2.getBytes();
		byte[] actual = "i like icecream and mango\ni like icecream and man go".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

	@Test
	public void testStage3() {

		String stage3 = app.stage3(CONS_INPUT, CONS_DIC, CONS_CUST_DIC);

		byte[] expected = stage3.getBytes();
		byte[] actual = "i like icecream and mango\ni like ice cream and man go\ni like icecream and man go".getBytes();

		assertArrayEquals("failure - byte arrays not same", expected, actual);

	}

}
