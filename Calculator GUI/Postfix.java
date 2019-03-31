import java.util.Stack;

public class Postfix
{
    String expr;
    Stack<Double> stack;
    
    public Postfix(String e)
    {
        expr = e;
        stack = new Stack<Double>();
    }
    
    public double eval()
    {
        String[] tokens = expr.split(" ");
        
        for (String token : tokens)
        {   
            //Handles extra spaces
            if( token.equals("") ) 
            {
             //do nothing   
            }
            //Operators requiring 1 operand
            else if( token.equals("sqrt") || token.equals("dup") || token.equals("!") )
            {
                double a = stack.pop();
                
                switch( token )
                {
                   case "sqrt" :
                        stack.push( Math.sqrt(a) );
                        break;
                   case "dup" :
                        stack.push(a);
                        stack.push(a);
                        break;
                   case "!" :
                        stack.push( factorial(a) );
                        break;
                        
                }
                
            }
            //Operators requiring 2 operands
            else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^") || token.equals("swap") || token.equals("%") ) 
            {
                double a = stack.pop();
                double b = stack.pop();
                
                switch( token )
                {
                    case "+" :
                                stack.push(a+b);
                                break;
                    case "-" :
                                stack.push(a-b);
                                break;
                    case "*" :
                                stack.push(a*b);
                                break;
                    case "/" :
                                if( b == 0)
                                {
                                    throw new MathError();
                                }
                                else
                                {
                                    stack.push(a/b);
                                }
                                break;
                    case "^" :
                                stack.push( Math.pow(a,b) );
                                break;
                    case "swap" :
                                stack.push(a);
                                stack.push(b);
                                break;
                    case "%" :
                                stack.push(a % b );
                                break;
                }
            }
            //Operators requiring 3 operands
            else if ( token.equals("rot") )
            {
                double a = stack.pop();
                double b = stack.pop();
                double c = stack.pop();
                
                switch(token)
                {
                    case "rot" :
                        stack.push(a);
                        stack.push(b);
                        stack.push(c);
                        break;
                }
            }
            //Numbers
            else
            {
                if( token.equals("pi") || token.equals("π") )
                {
                    stack.push(Math.PI);
                }
                else
                {
                    try
                    {
                        double n = Double.parseDouble(token);
                        stack.push(n);
                    }
                    catch (java.lang.NumberFormatException nfe)
                    {
                        throw new UnknownToken();
                    }   
                }
                             
            }
        }
        
        return stack.peek();
    }
    
    //Helper functions
    private double factorial( double x )
    {
        int n = (int) x; //Casts the number as an integar for efficiency
        int product = 1;
        
        if( n < 0 || n != x)
        {
            throw new MathError();
        }       
        else if( n == 0 || n == 1 ) 
        {
            return 1;
        }
        else
        {
            for( int i = 2; i <= n; i++ )
            {
                product *= i;
            }
        }
        
        return product;
    }
}