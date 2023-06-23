package com.larissa.android.quiz;

import android.util.Log;

import java.util.Stack;

public class Calculate {
    /*
    计算公式逻辑：判断字符
    ①为数字，则一直取到数字结束
    ②为操作符，先判断是否为符号，再判断与栈顶的操作符操作优先级，如果优先级低于栈顶的操作符，则进行计算再存入数
    ③为左括号，记录当前数据栈和操作栈的元素个数之和，并令左括号数+1，并且后续对算式直接入栈不判断优先级
    ④为右括号，如果左括号数为0返回错误，如果左括号数大于0，则记录下当前两个栈的元素之和与上一个左括号记录的元素之和的差值，进行算式复原，使用迭代计算括号内的数据
    ⑤为三角函数、根号、ln、log时，操作与左括号相同，在右括号进行判断时如果栈顶为这些函数，则直接进行计算并入栈
    */
    public static String calculate(String equation){

        String TAG="333";
        Stack<Double> number = new Stack<>();
        Stack<Character> operator = new Stack<>();
        int left_bracket_count=0;
        Stack<Integer> left_location = new Stack<>();
        boolean isNe=false;

        for (int i = 0; i < equation.length(); i++){
            char token = equation.charAt(i);//获取等式中第i个字符
            //如果字符为数字
            if(Character.isDigit(token)){
                double num=token-'0';
                int decimal=0;
                while(++i<equation.length()&&Character.isDigit(equation.charAt(i))){
                    num=num*10+equation.charAt(i)-'0';
                }//计算整数部分
                if(i<equation.length()&&equation.charAt(i)=='.'){//当下一个字符为.时，计算小数部分
                    i++;
                    if(Character.isDigit(equation.charAt(i))) {
                        decimal = equation.charAt(i) - '0';
                        while (++i < equation.length() && Character.isDigit(equation.charAt(i))) {
                            decimal = decimal * 10 + equation.charAt(i) - '0';
                        }
                    }
                }
                num=num+decimal*Math.pow(10,-Integer.toString(decimal).length());
                if(isNe){number.push(0-num);isNe=false;}//当取负标志位为T时取负
                else number.push(num);
                i--;
            } else if (token=='e'||token=='π') {
                if(token=='e')number.push(2.718);
                else number.push(3.141);
            } else if (isOperate(token)) {
                //当第一位或前一位为操作符式这个-号被认为是负号
                if(token=='-'&&(i==0||(!Character.isDigit(equation.charAt(i-1))&&equation.charAt(i-1)!='e'&&equation.charAt(i-1)!='π'))){
                    isNe=true;
                }else {
                    while (!operator.isEmpty() && comparePrecedence(operator.peek(), token) && left_bracket_count == 0) {
                        operate(number, operator);//当优先级小于栈最顶层时进行计算
                    }
                    if (token == '÷') operator.push('/');
                    else operator.push(token);
                }
            } else if (token=='(') {
                left_bracket_count++;
                left_location.push(number.size()+operator.size());
            } else if (token==')') {
                if(left_bracket_count==0)return "Error!";
                else{
                    int count=number.size()+operator.size()-left_location.pop();
                    String sub="";
                    for(int num=0;num<count;num++) {
                        if(num%2==0)sub=number.pop().toString()+sub;
                        else {
                            char op=operator.pop();
                            if(op=='/')sub='÷'+sub;
                            else sub=op+sub;
                        }
                    }
                    number.push(Double.parseDouble(Calculate.calculate(sub)));
                    if(operator.peek()=='s'||operator.peek()=='c'||operator.peek()=='t'||operator.peek()=='√'||operator.peek()=='I'||operator.peek()=='L'){
                        Trifun(number,operator);
                    }
                    left_bracket_count--;
                }
            } else if (token=='!'){//阶乘直接计算并存入数
                double calnum=number.pop();
                if(!isInteger(calnum))return "Error!";
                else  {
                    number.push(Factorial(calnum));
                }
            } else if (token=='s'||token=='c'||token=='t') {
                operator.push(token);
                i+=3;
                left_bracket_count++;
                left_location.push(number.size()+operator.size());
            } else if (token=='√') {
                operator.push('√');
                i++;
                left_bracket_count++;
                left_location.push(number.size()+operator.size());
            }else if(token=='l'){
                if(equation.charAt(i+1)=='n'){
                    operator.push('I');
                    i+=2;
                } else if (equation.charAt(i+1)=='o') {
                    operator.push('L');
                    i+=3;
                }
                left_bracket_count++;
                left_location.push(number.size()+operator.size());
            }
        }
        while (!operator.isEmpty()) {
            operate(number,operator);
        }
        double num=number.pop();
        if(isInteger(num)){
            return Integer.toString((int)num);
        } else if (Double.toString(num)=="Infinity") {
            return "算式错误";
        } else{
            return Double.toString(num);
        }
    }

    private static boolean isOperate(char token){
        return token=='+'||token=='-'||token=='*'||token=='÷'||token=='^';
    }

    private static boolean comparePrecedence(char token1,char token2){
        int pr1=getPrecedence(token1);
        int pr2=getPrecedence(token2);
        return pr1>pr2;
    }

    private static int getPrecedence(char token){
        if(token=='+'||token=='-') {
            return 1;
        }
        else if (token=='*'||token=='÷') {
            return 2;
        }
        else if (token=='^') {
            return 3;
        } else return 0;
    }

    private static void operate(Stack<Double> number,Stack<Character> operator){
        char op = operator.pop();
        double right_num = number.pop();
        double left_num = number.pop();
        switch (op){
            case '+':if(!operator.isEmpty()&&operator.peek()=='-') {
                operator.pop();
                operator.push('+');
                left_num=-left_num;
            }
                number.push(left_num + right_num);break;
            case '-':number.push(left_num-right_num);break;
            case '*':number.push(left_num*right_num);break;
            case '/':number.push(left_num/right_num);break;
            case '^':number.push(Math.pow(left_num,right_num));break;
            default:break;
        }
    }

    private static boolean isInteger(double num){
        int intNum = (int)num;
        return intNum==num;
    }

    //求阶乘
    private static double Factorial(double num){
        double result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    //二分法求开根
    private static double SquareRoot(double num){
        if(num<0)return -1;
        else {
            double eps = 1e-8;
            double a = 0, b = num;
            while (b - a > eps) {
                double m = (b + a) / 2;
                if (m * m > num) b = m;
                else a = m;
            }
            return a;
        }
    }

    private static void Trifun(Stack<Double> number,Stack<Character> operator){
        char op = operator.pop();
        double num = number.pop();
        switch (op){
            case 's':number.push(Sincal(num));break;
            case 'c':number.push(Coscal(num));break;
            case 't':number.push(Sincal(num)/Coscal(num));break;
            case '√':number.push(SquareRoot(num));break;
            case 'I':number.push(Math.log(num));break;
            case 'L':number.push(Math.log10(num));break;
            default:break;
        }
    }

    private static double Sincal(double num){
        double result=0;
        for (int i = 0; i < 10; i++) {
            double term = Math.pow(-1, i) * Math.pow(num, 2 * i + 1) / Factorial(2 * i + 1);
            result += term;
        }
        if(result<1e-3)return 0;
        return result;
    }

    private static double Coscal(double num){
        double result=0;
        for (int i = 0; i < 10; i++) {
            double term = Math.pow(-1, i) * Math.pow(num, 2 * i ) / Factorial(2 * i );
            result += term;
        }
        if(result<1e-3)return 0;
        return result;
    }

}
