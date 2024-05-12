/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblog_pi.myblog;

import myblog_pi.Model.Forum_Commentaire;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author ghada
 */
public class listViewCommentaires {

   public static void setItems(ListView<Forum_Commentaire> listView, ObservableList<Forum_Commentaire> listeCommentaires) {
        listView.setItems(listeCommentaires);
    }
}
