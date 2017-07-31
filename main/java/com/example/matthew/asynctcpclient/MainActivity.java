package com.example.matthew.asynctcpclient;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {


    public Button upBtn;
    public Button dowBtn;
    public Button lefBtn;
    public Button rigBtn;
    public Button subBtn;
    public EditText ipText;
    public EditText portText;
    public EditText msgText;
    public TextView rcvdText;


    private String data = null;
    private Socket socket;
    public PrintWriter out;
    public BufferedReader in ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        GetViewsByID();
        SetListeners();

    }

    public void GetViewsByID(){
        this.subBtn = (Button)findViewById(R.id.submitBtn);
        this.upBtn = (Button)findViewById(R.id.btnU);
        this.dowBtn = (Button)findViewById(R.id.btnD);
        this.rigBtn = (Button)findViewById(R.id.btnR);
        this.lefBtn = (Button)findViewById(R.id.btnL);
        this.ipText = (EditText)findViewById(R.id.ipInput);
        this.portText = (EditText)findViewById(R.id.portInput);
        this.msgText = (EditText)findViewById(R.id.msgInput);
        this.rcvdText = (TextView)findViewById(R.id.rcvOutput);
    }

    public void SetListeners(){
        //If Submit btn is pressed, get IP and Port, get msg(optional)
        this.subBtn.setOnClickListener(new HandleClickEvent());
        this.upBtn.setOnClickListener(new HandleClickEvent());
        this.dowBtn.setOnClickListener(new HandleClickEvent());
        this.lefBtn.setOnClickListener(new HandleClickEvent());
        this.rigBtn.setOnClickListener(new HandleClickEvent());
    }

    public class HandleClickEvent implements View.OnClickListener {
        public void onClick(View arg0) {
            String myIp = ipText.getText().toString();
            String myPort = portText.getText().toString();
//            Toast.makeText(MainActivity.this, "This is my Toast message!",
//                    Toast.LENGTH_LONG).show();

            String btnText = ((Button)arg0).getText().toString();
            if(btnText.contains("Send")){
                String suppliedText = msgText.getText().toString();
                if(suppliedText.isEmpty()){
                    btnText = "Marco!";
                }
            }

            try{
                String[] mytaskparams = {myIp.trim(),myPort.trim(),btnText.trim()};

                new AsyncAction().execute(mytaskparams);

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class AsyncAction extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... args) {
            try {
                InetAddress serverAddr = InetAddress.getByName(args[0]);
                socket = new Socket(serverAddr, Integer.parseInt(args[1]));
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println(args[2]);
                out.flush();

            } catch (IOException e) {}
            catch (Exception e){}

            return null;//returns what you want to pass to the onPostExecute()
        }

        @Override
        protected void onPostExecute(Void v){

        }

    }
}
