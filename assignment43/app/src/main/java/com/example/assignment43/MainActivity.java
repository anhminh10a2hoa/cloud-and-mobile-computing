package com.example.assignment43;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button buttonDel;
    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiply;
    Button buttonDivide;
    Button buttonEqual;
    TextView textView;
    TextView textError;
    private String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = findViewById(R.id.button);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='0';
                textView.setText(expression);
            }
        });
        button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='1';
                textView.setText(expression);
            }
        });
        button2 = findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='2';
                textView.setText(expression);
            }
        });
        button3 = findViewById(R.id.button4);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='3';
                textView.setText(expression);
            }
        });
        button4 = findViewById(R.id.button5);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='4';
                textView.setText(expression);
            }
        });
        button5 = findViewById(R.id.button6);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='5';
                textView.setText(expression);
            }
        });
        button6 = findViewById(R.id.button19);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='6';
                textView.setText(expression);
            }
        });
        button7 = findViewById(R.id.button20);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='7';
                textView.setText(expression);
            }
        });
        button8 = findViewById(R.id.button21);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='8';
                textView.setText(expression);
            }
        });
        button9 = findViewById(R.id.button22);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='9';
                textView.setText(expression);
            }
        });
        buttonDel = findViewById(R.id.button23);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expression.length() == 0) {
                    String error = "can not delete";
                    textError.setText(error);
                    new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                textError.setText("");
                            }
                        },
           3000);
                } else {
                    expression=expression.substring(0, expression.length() - 1);
                    textView.setText(expression);
                }

            }
        });
        buttonPlus = findViewById(R.id.button24);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='+';
                textView.setText(expression);
            }
        });
        buttonMinus = findViewById(R.id.button25);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='-';
                textView.setText(expression);
            }
        });
        buttonMultiply = findViewById(R.id.button26);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='*';
                textView.setText(expression);
            }
        });
        buttonDivide = findViewById(R.id.button27);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='/';
                textView.setText(expression);
            }
        });
        buttonEqual = findViewById(R.id.button7);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double newResult = eval(expression);
                expression=Double.toString(newResult);
                textView.setText(Double.toString(newResult));
            }
        });
        textView = findViewById(R.id.text);
        textError = findViewById(R.id.text_error);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}