import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionListener;

public class Server_test implements  ActionListener
{
    JLabel jl;
    String msg="伺服器狀態 : 連線中\n";
    String theStrDestDir="D:/";
    static int port=81;
    String fileName="";
    public static void main(String[] args) 
    { 
        Server_test sv=new Server_test();//檔案傳送到程式根目錄
        sv.setGUI();
        sv.startServer(port);
    }
    public void startServer(int port)
    {
        try { 
            msg="簡易檔案接收....\n";
            this.refresh();
            System.out.println("簡易檔案接收..."); 
            msg="將接收檔案於連接埠: "+port+"\n";
            this.refresh();
            System.out.printf("將接收檔案於連接埠: %d%n", port); 
            ServerSocket serverSkt = new ServerSocket(port); 
            while(true) 
            { 
                msg="傾聽中....\n";
                this.refresh();
                //System.out.println("傾聽中...."); 
                Socket clientSkt = serverSkt.accept();
                msg="與"+clientSkt.getInetAddress().toString()+"建立連線\n";
                this.refresh();
                System.out.printf("與 %s 建立連線%n", clientSkt.getInetAddress().toString());  
                // 取得檔案名稱
                fileName = new BufferedReader(
                                    new InputStreamReader(
                                      clientSkt.getInputStream())).readLine();
                msg="接收檔案 "+fileName+"....\n";
                this.refresh();
                System.out.printf("接收檔案 %s ...", fileName); 
                BufferedInputStream inputStream = 
                    new BufferedInputStream(clientSkt.getInputStream()); 
                BufferedOutputStream outputStream = 
                    new BufferedOutputStream(new FileOutputStream(new File(theStrDestDir+"\\"+fileName))); 
                int readin; 
                while((readin = inputStream.read()) != -1) { 
                    outputStream.write(readin);
                    Thread.yield();
                } 

                outputStream.flush();
                outputStream.close();                
                inputStream.close(); 
                
                clientSkt.close(); 
                msg="檔案接收完畢!\n";
                this.refresh();
                System.out.println("\n檔案接收完畢！"); 
                System.out.println();
                //System.out.println(this.getClass().getResource("/").);
                //copyFile(oldPath, newPath);
                //delFile(oldPath);
            } 
        } 
        catch(Exception e) 
        { 
            e.printStackTrace(); 
        } 
    }
    public void refresh()
    {
       jl.setText(msg);
       jl.validate();
    }
    public void setGUI()
    {
        JFrame f=new JFrame("UploadServer");
        JButton jb_close=new JButton("關閉伺服器");//關閉伺服器
        JButton jb_set=new JButton("設定連接port");//設定port
        JButton jb_loc = new JButton("路徑設定");//設定port
        jl=new JLabel();//主機狀態
         JLabel icon;
        icon = new JLabel(new ImageIcon("server.gif"));
        jb_close.addActionListener(this);
        jb_set.addActionListener(this);
        jb_loc.addActionListener(this);
        icon.setBounds(0, 0, 150, 150);
        
        jl.setBounds(175, 100, 200, 100);
        jl.setText(msg);
        jb_close.setBounds(170, 20, 110, 25);
        jb_set.setBounds(170, 60, 110, 25);
        jb_loc.setBounds(170, 100, 110, 25);
        f.add(icon);
        f.add(jl);
        f.add(jb_set);
        f.add(jb_close);
        f.add(jb_loc);
        f.getContentPane().setLayout(null);
        f.setBounds(10, 100, 300, 200);
        f.setVisible(true);
          f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            //主視窗增加到視窗監聽器中
            f.addWindowListener(new WindowAdapter() 
            {//視窗關閉執行的動作
              public void windowClosing(WindowEvent e) 
              {//宣告整數result存放視窗執行的動作,line:147~151設定關閉視窗時 詢問是否關閉視窗的樣式
                int result=JOptionPane.showConfirmDialog
                            (f,
                            "確定要結束程式嗎?",
                            "確認訊息",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                //確定關閉視窗
                    if (result==JOptionPane.YES_OPTION)
                        {//結束程式
                            System.exit(0);
                        }
                }    
            });
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) 
    {
      
        if(ae.getActionCommand().startsWith("關"))
        {
            System.exit(0);
        }
        else if(ae.getActionCommand().startsWith("設"))
        {
            
            JFrame f1=new JFrame();
            JOptionPane.showMessageDialog(f1,"未來擴充的功能,尚未開放");
            String input=JOptionPane.showInputDialog("請輸入要設定的port,預設為81");
            JOptionPane.showMessageDialog(f1,"您輸入的是 : " + input);
            
            port=Integer.parseInt(input);
                
                
        }
        else if(ae.getActionCommand().startsWith("路"))
        {
            JFileChooser file = new JFileChooser (".");
            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
            int returnValue = file.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                theStrDestDir=file.getSelectedFile().getAbsolutePath();//設定路徑
                JOptionPane.showMessageDialog(null, "\n新的下載路徑 : "+theStrDestDir,"設定路徑", JOptionPane.PLAIN_MESSAGE );//顯示路徑
            }
        }
    }
    
    
    
}
