import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by taka on 2016/01/13.
 */
public class MusicPlayer extends JPanel {
    public MusicPlayer(Players players) {
        super(new BorderLayout());
        JFrame frame;
        frame = new JFrame();
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Panel topPanel = new Panel();
        Panel middlePanel = new Panel();
        Panel bottomPanel = new Panel();

        //top
        JLabel playing = new JLabel();
        topPanel.add(playing, BorderLayout.CENTER);
        topPanel.add(new JButton(new AbstractAction("戻る") {
            @Override public void actionPerformed(ActionEvent e) {
                players.moveBackward();
                playing.setText(players.getMusicNames());
            }
        }), BorderLayout.WEST);
        topPanel.add(new JButton(new AbstractAction("進む") {
            @Override public void actionPerformed(ActionEvent e) {
                players.moveForward();
                playing.setText(players.getMusicNames());
            }
        }), BorderLayout.EAST);

        //middle
        DefaultTableModel playListTableModel = new DefaultTableModel(new String[] {"music"}, 0);
        JTable playlistTable = new JTable(playListTableModel);
        playlistTable.getColumn("music").setPreferredWidth(375);
        playlistTable.setRowHeight(25);
        middlePanel.add(playlistTable);

        //bottom
        bottomPanel.add(new JButton(new AbstractAction("読み込み") {
            @Override public void actionPerformed(ActionEvent e) {
                for (String music : players.getPlayList()) {
                    playListTableModel.removeRow(0);
                }
                players.makeMusicList();
                for (String music : players.getPlayList()) {
                    playListTableModel.addRow(new String[] {music});
                }
                players.loadMusics();
            }
        }), BorderLayout.LINE_START);
        bottomPanel.add(new JButton(new AbstractAction("プレイリスト") {
            @Override public void actionPerformed(ActionEvent e) {
                for (String music : players.getPlayList()) {
                    playListTableModel.removeRow(0);
                }
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                players.loadPlayList(chooser.getSelectedFile());
                for (String music : players.getPlayList()) {
                    playListTableModel.addRow(new String[] {music});
                }
                players.loadMusics();
            }
        }), BorderLayout.CENTER);
        bottomPanel.add(new JButton(new AbstractAction("再生/停止") {
            @Override public void actionPerformed(ActionEvent e) {
                if (players.isPlaying()) {
                    players.pauseMusics();
                } else {
                    players.playMusics();
                }
                playing.setText(players.getMusicNames());
            }
        }), BorderLayout.LINE_END);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        Players players = new Players();
        new MusicPlayer(players);
    }
}
