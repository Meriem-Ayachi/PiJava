/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblog_pi.myblog;

import myblog_pi.Model.Forum_Commentaire;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author ghada
 */
class tableViewCommentaires {

       public static void setItems(TableView<Forum_Commentaire> tableView, ObservableList<Forum_Commentaire> listeCommentaires) {
        // Assurez-vous d'avoir une colonne dans votre TableView pour chaque propriété de Forum_Commentaire
        tableView.setItems(listeCommentaires);
}
}
