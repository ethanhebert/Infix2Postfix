/*
Ethan Hebert
11-15-21
Infix2Postfix.java
A code that converts infix to postifx and evaluates the postfix.
*/

import java.util.*;
import java.lang.*;

class Infix2Postfix
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine())
        {
            //get the infix expression
            String infix = scanner.nextLine();

            //convert to postfix
            String postfix = Postfix(infix);

            //evaluate the postfix
            String evaluation = Evaluation(postfix);

            //print out infix
            System.out.println(infix);

            //print out postfix
            System.out.println(postfix);

            //print out evaluation
            System.out.println(evaluation);

            //print blank line
            System.out.println();
        }
        scanner.close();
    }

    public static String Postfix(String infix)
    {
        //declare necessary data
        char op;
        char token;
        
        //make the infix queue and fill it
        Queue<Character> infixQ = new Queue<Character>();

        for (int i=0; i<infix.length(); i++)
        {
            char c = infix.charAt(i);
            infixQ.Enqueue(c);
        }

        //make the empty postfix queue
        Queue<Character> postfixQ = new Queue<Character>();

        //make the empty operand stack
        Stack<Character> operS = new Stack<Character>();

        //go through the whole infix expression
        while (infixQ.IsEmpty() != true)
        {
            token = infixQ.Dequeue();
            
            //if token is an operand
            if (token == '0' || token == '1' || token == '2' || token == '3' || token == '4' || token == '5'
                || token == '6' || token == '7' || token == '8' || token == '9')
            {
                postfixQ.Enqueue(token);
            }

            //if token is a right parenthesis
            else if (token == ')')
            {
                op = operS.Pop();
                while (op != '(')
                {
                    postfixQ.Enqueue(op);
                    op = operS.Pop();
                }
            }

            //if token is a left parenthesis or operator
            else
            {
                op = operSPeek(operS);

                while (stack_priority(op) >= infix_priority(token))
                {
                    op = operS.Pop();
                    postfixQ.Enqueue(op);
                    op = operSPeek(operS);
                }
                operS.Push(token);
            }
        }

        //go through the remaining operator stack
        while (operS.IsEmpty() != true)
        {
            op = operS.Pop();
            postfixQ.Enqueue(op);
        }

        //return the postfix as a string
        return postfixQ.toString();
    }

    public static char operSPeek(Stack<Character> operS)
    {
        char op;

        try
        {
            op = operS.Peek();
        }
        catch (Exception e) //if the stack is empty, set the op to nothing just to keep code running
        {
            op = '\u0000';
        }

        return op;
    }

    public static int stack_priority(char op)
    {
        if (op == '^')
            return 2;
        else if (op == '*')
            return 2;
        else if (op == '/')
            return 2;
        else if (op == '+')
            return 1;
        else if (op == '-')
            return 1;
        else 
            return 0;
    }

    public static int infix_priority(char token)
    {
        if (token == '(')
            return 4;
        else if (token == '^')
            return 3;
        else if (token == '*')
            return 2;
        else if (token == '/')
            return 2;
        else if (token == '+')
            return 1;
        else if (token == '-')
            return 1;
        else 
            return 0;
    }

    public static String Evaluation(String postfix)
    {
        //declare the variables
        float a;
        float b;

        //make the postfix queue and fill it
        Queue<String> postfixQ = new Queue<String>();

        for (int i=0; i<postfix.length(); i++)
        {
            char c = postfix.charAt(i);
            String s = String.valueOf(c); 
            postfixQ.Enqueue(s);
        }

        //make an empty postfix stack
        Stack<String> postfixS = new Stack<String>();

        //go through the whole postfixQ
        while (postfixQ.IsEmpty() != true)
        {
            String token = postfixQ.Dequeue();

            //if token is an operand
            if (token.equals("0") || token.equals("1") || token.equals("2") || token.equals("3") || token.equals("4") || token.equals("5")
                || token.equals("6") || token.equals("7") || token.equals("8") || token.equals("9"))
            {
                postfixS.Push(token);
            }

            //if token is anything else (not a number)
            else 
            {
                a = Float.parseFloat(postfixS.Pop());
                b = Float.parseFloat(postfixS.Pop());

                String result = Result(a, b, token);

                postfixS.Push(result);
            }
        }

        //return the last element of the stack, the answer
        return postfixS.Pop();
    }

    public static String Result(float a, float b, String token)
    {
        float result;

        //do the actual math and store in float result
        if (token.equals("^"))
            result = (float)Math.pow(b,a);
        else if (token.equals("*"))
            result = b*a;
        else if (token.equals("/"))
            result = b/a;
        else if (token.equals("+"))
            result = b+a;
        else if (token.equals("-"))
            result = b-a;
        else 
            result = 0.0f;

        //convert float back to string
        String stringResult = String.valueOf(result);

        //return the string result
        return stringResult;
    }
}