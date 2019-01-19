import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextArea textArea;

    @FXML
    TextField msgField;

    public void sendMsg(ActionEvent actionEvent) {
        textArea.appendText(msgField.getText() + '\n');
        msgField.clear();
        msgField.requestFocus();
    }
}
