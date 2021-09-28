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
    private String expression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button0 = (Button) findViewById(R.id.button);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='0';
                textView.setText(expression);
            }
        });
        button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='1';
                textView.setText(expression);
            }
        });
        button2 = (Button) findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='2';
                textView.setText(expression);
            }
        });
        button3 = (Button) findViewById(R.id.button4);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='3';
                textView.setText(expression);
            }
        });
        button4 = (Button) findViewById(R.id.button5);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='5';
                textView.setText(expression);
            }
        });
        button5 = (Button) findViewById(R.id.button6);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='5';
                textView.setText(expression);
            }
        });
        button6 = (Button) findViewById(R.id.button19);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='6';
                textView.setText(expression);
            }
        });
        button7 = (Button) findViewById(R.id.button20);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='7';
                textView.setText(expression);
            }
        });
        button8 = (Button) findViewById(R.id.button21);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='8';
                textView.setText(expression);
            }
        });
        button9 = (Button) findViewById(R.id.button22);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='9';
                textView.setText(expression);
            }
        });
        buttonDel = (Button) findViewById(R.id.button23);
        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression=expression.substring(0, expression.length() - 1);
                textView.setText(expression);
            }
        });
        buttonPlus = (Button) findViewById(R.id.button24);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='+';
                textView.setText(expression);
            }
        });
        buttonMinus = (Button) findViewById(R.id.button25);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='-';
                textView.setText(expression);
            }
        });
        buttonMultiply = (Button) findViewById(R.id.button26);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+='*';
                textView.setText(expression);
            }
        });
        buttonDivide = (Button) findViewById(R.id.button27);
        buttonDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression+=':';
                textView.setText(expression);
            }
        });
        buttonEqual = (Button) findViewById(R.id.button7);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newResult = eval(expression);
                expression=Integer.toString(newResult);
                textView.setText(Integer.toString(newResult));
            }
        });
        textView = (TextView) findViewById(R.id.text);
    }

    public int eval(final String str) {
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

            int parse() {
                nextChar();
                int x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            int parseExpression() {
                int x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            int parseTerm() {
                int x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            int parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                int x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Integer.parseInt(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}