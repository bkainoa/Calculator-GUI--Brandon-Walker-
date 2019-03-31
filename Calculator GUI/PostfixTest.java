

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class PostfixTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PostfixTest
{
    /**
     * Default constructor for test class PostfixTest
     */
    public PostfixTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testEval()
    {
        // Basic test
        Postfix b1 = new Postfix("4");
        assertEquals(4.0, (double)b1.eval(), 0.0001);
        
        Postfix b2 = new Postfix("4 dup +");
        assertEquals(8.0, (double)b2.eval(), 0.0001);
        
        Postfix b3 = new Postfix("1 2 swap");
        assertEquals(1.0, (double)b3.eval(), 0.0001);
        
        Postfix b4 = new Postfix("1 2 3 rot");
        assertEquals(1.0, (double)b4.eval(), 0.0001);
        
        // Addition test 
        Postfix add1 = new Postfix("1 2 +");
        assertEquals(3.0, (double)add1.eval(), 0.0001);
        
        Postfix add2 = new Postfix("1 2 + 3 +");
        assertEquals(6.0, (double)add2.eval(), 0.0001);
        
        // Subtraction test     
        Postfix sub1 = new Postfix("1 2 - 3 4 - -");
        assertEquals(0.0, (double)sub1.eval(), 0.0001);
        
        Postfix sub2 = new Postfix("10 5 -");
        assertEquals(-5.0, (double)sub2.eval(), 0.0001);
        
        // Multiplication test       
        Postfix mul1 = new Postfix("18 27 * 4 *");
        assertEquals(1944.0, (double)mul1.eval(), 0.0001); 
        
        Postfix mul2 = new Postfix("1.75 18 *");
        assertEquals(31.5, (double)mul2.eval(), 0.0001);
        
        // Division test
        Postfix div1 = new Postfix("5 250 / 2 swap /");
        assertEquals(25.0, (double)div1.eval(), 0.0001);
        
        Postfix div2 = new Postfix("0.3654 45 /");
        assertEquals(123.1527093596, (double)div2.eval(), 0.0000000001);
        
        // Exponentiation test
        Postfix exp1 = new Postfix("7 3 ^");
        assertEquals(2187, (double)exp1.eval(), 0.0001);
        
        Postfix exp2 = new Postfix("999 1 ^");
        assertEquals(1, (double)exp2.eval(), 0.0001);
        
        Postfix exp3 = new Postfix("5 3 ^");
        assertEquals(243.0, (double)exp3.eval(), 0.0001);
        
        // Square Root test
        Postfix sqrt1 = new Postfix("2 sqrt");
        assertEquals(1.4142, (double)sqrt1.eval(), 0.0001);
               
        Postfix sqrt2 = new Postfix("7 sqrt 9 sqrt *");
        assertEquals(3*Math.sqrt(7), (double)sqrt2.eval(), 0.000001);
        
        //Combined tests
        Postfix comb1 = new Postfix("2 7 ^ sqrt");
        Postfix comb2 = new Postfix("2 7 sqrt ^");
        assertEquals( (double)comb1.eval(), (double)comb2.eval(), 0.0001 );
        
        Postfix comb3 = new Postfix("5 sqrt 1 + 2 swap /");
        assertEquals( 1.618033989, (double)comb3.eval(), 0.000001);
        
        for( int x = 0; x <= 3; x++ )
        {
            Postfix cosx = new Postfix("2 ! 2 " + x + " ^ / 1 - 4 ! 4 " + x + " ^ / + 6 ! 6 " + x + " ^ / - 8 ! 8 " + x + " ^ / - 10 ! 10 " + x + " ^ / +");
            assertEquals( Math.cos(x), (double)cosx.eval(), 0.05);
        }
        
        
        for( int x = 0; x <= 3; x++ )
        {
            Postfix sinx = new Postfix("3 ! 3 " + x + " ^ / " + x + " - 5 ! 5 " + x + " ^ / + 7 ! 7 " + x + " ^ / - 9 ! 9 " + x + " ^ / - 11 ! 11 " + x + " ^ / +");
            assertEquals( Math.sin(x), (double)sinx.eval(), 0.05);
        }
    }
}
