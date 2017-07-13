import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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


    private String data = null;
    private Socket socket;
    public PrintWriter out;
    public BufferedReader in ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    new AsyncAction().execute();

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class AsyncAction extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... args) {
            try {
                InetAddress serverAddr = InetAddress.getByName("192.168.254.38");
                socket = new Socket(serverAddr, 8080);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("hello");
                out.flush();

            } catch (IOException e) {}
            catch (Exception e){}

            return null;//returns what you want to pass to the onPostExecute()
        }


    }
}
