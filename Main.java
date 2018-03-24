import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(300, 400);

        // ボタンに画像を設定して、表示
        JButton btn = new JButton(new ImageIcon("icon.png"));
        frame.getContentPane().add(btn);

        // ボタン押下でフェードアウトするアクションを設定
        btn.addActionListener(action);

        // frameの表示
        frame.setVisible(true);
    }
    
    /**
     * ボタン押下時のフェードアウト処理
     */
    static ActionListener action = event -> {
        // ボタンを取得
        final JButton btn = (JButton)event.getSource();

        // フェードアウトアニメの実行部分
        final Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            /** アルファ値(透過具合の値)255が透過なし、0が完全透明 */
            public int alf = 0xFF;
            @Override public void actionPerformed(ActionEvent e) {
                // ボタンから画像を抜き取る
                Icon icon = btn.getIcon();
                ImageIcon imageicon = (ImageIcon)icon;
                Image image = imageicon.getImage();

                // アルファ値を下げて、画像を生成
                Image alphaImage = getAlphaImage(image, alf--);
                
                // ボタンに画像を再設定
                btn.setIcon(new ImageIcon(alphaImage));

                // アルファが0(完全に透明)になったらアニメ終了
                if (alf == 0) timer.stop();
            }
        });
        // フェードアウトのアニメ開始
        timer.start();
    };


    /** 
     * 参考URLのほぼコピペ
     * @param img 画像
     * @param alpha 設定するアルファ値
     * @return 指定したのアルファ値で作った画像
     */
    static public Image getAlphaImage(Image img, int alpha) {
        if (img == null) return null;
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        //ピクセル値取得
        int[] pixel = new int[width * height];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixel, 0, width);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            return null;
        }
        // ★★透明化(ここは変更ポイント)★★
        int color = alpha << (8*3);
        for (int i = 0; i < width * height; i++) {
            pixel[i] &= 0x00FFFFFF;// alphaのビットを落とす
            pixel[i] |= color; // alphaのビットを設定
        }

        //イメージに戻す
        Image alphaImage = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pixel, 0, width));
        return alphaImage;
    }
}
