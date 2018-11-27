package com.baonguyen.robotaq;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends Activity {

    private Socket socket;
    private int dem = 0;

    private EditText edtDiaChiIP, edtDiaChiCong;
    ToggleButton tgConnect;

    private static String SERVERPORT = "";
    private static String SERVER_IP = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();

    }

    private void findViewById() {
        tgConnect = (ToggleButton) findViewById(R.id.myButtonConnect);
        edtDiaChiIP = (EditText) findViewById(R.id.diaChiIP);
        edtDiaChiCong = (EditText) findViewById(R.id.diaChiCong);

        tgConnect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    onClickConnect();
                } else {
                    tgConnect.setBackground(getResources().getDrawable(R.drawable.circle));
                    try {
                        onClickDisconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void guiDuLieu() {

        try {
            if (dem == 1) {
                String str = "U";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 2) {
                String str = "D";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 3) {
                String str = "L";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 4) {
                String str = "R";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 5) {
                String str = "S";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 6) {
                String str = "A";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
            if (dem == 7) {
                String str = "B";
                PrintWriter out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
                out.println(str);
                dem = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickA(View view) {
        dem = 6;
        guiDuLieu();
    }

    public void onClickB(View view) {
        dem = 7;
        guiDuLieu();
    }

    public void onClickDown(View view) {
        dem = 2;
        guiDuLieu();
    }

    public void onClickRight(View view) {
        dem = 4;
        guiDuLieu();
    }

    public void onClickStop(View view) {
        dem = 5;
        guiDuLieu();
    }

    public void onClickLeft(View view) {
        dem = 3;
        guiDuLieu();
    }

    public void onClickUp(View view) {
        dem = 1;
        guiDuLieu();
    }

    public void onClickConnect() {
        // get the ip address
        String ipAddress = edtDiaChiIP.getText().toString().trim();
        // get the port number
        String portNumber = edtDiaChiCong.getText().toString().trim();

        SERVER_IP = ipAddress;
        SERVERPORT = portNumber;

        if (ipAddress.isEmpty() || portNumber.isEmpty()) {
            Toast.makeText(this, "Không thể kết nối tới Server\nBạn xem lại địa chỉ IP và Port", Toast.LENGTH_LONG).show();
            tgConnect.setChecked(false);
        } else {
            new Thread(new ClientThread()).start();
            tgConnect.setBackground(getResources().getDrawable(R.drawable.denbat));
        }
    }


    public void onClickDisconnect() throws IOException {
        new Thread(new ClientThread()).interrupt();
        socket.close();
        if (socket.isClosed()) {
            Toast.makeText(this, "Đã Ngắt Kết Nối", Toast.LENGTH_LONG).show();
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, Integer.parseInt(String.valueOf(SERVERPORT)));
            } catch (IOException e1) {
                e1.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
