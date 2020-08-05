package com.tckxjzc.tzserver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.exit) Button exit;
    @BindView(R.id.btn_1) Button btn1;
    @BindView(R.id.btn_2) Button btn2;
    @BindView(R.id.tips) TextView tips;
    @BindView(R.id.address) TextView address;
    private boolean started=false;
    HttpServer server;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        start();
    }

    private void start(){


//        Document doc = Jsoup.parse(HttpTest.testHtml,ServerConfig.BASE_URL);
//        Log.d("Document",doc.outerHtml());
//        Log.d("Head",doc.head().outerHtml());


        started=true;
        startServer();
        tips.setText("服务已启动！！");
        url="http://"+getIpAddressString()+"";
        address.setText("地址:"+url+":"+ServerConfig.PORT);

        btn1.setOnClickListener(view  -> {
            if(!started){
                started=true;
                startServer();
                tips.setText("服务已启动！！");
                url="http://"+getIpAddressString()+"";
                address.setText("地址:"+url+":"+ServerConfig.PORT);
            }

        });
        exit.setOnClickListener(view->{
            if(server!=null){
                server.stop();
            }
            this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });
        btn2.setOnClickListener(view->{
            openBrowser();
        });
    }
    public void startServer(){
        server=new  HttpServer(this);
        server.start();
    }

    public  String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }
    public void openBrowser(){
        if(url==null){
            return;
        }
        Uri uri = Uri.parse("http://localhost:"+ServerConfig.PORT);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }


}
