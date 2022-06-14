package com.example.mas_gui.view;

import com.example.mas_gui.model.Sender;
import com.example.mas_gui.service.SenderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route("")
@PageTitle("Senders list")
public class SenderListView extends VerticalLayout {
    SenderService senderService;
    H1 h1SendersList = new H1("Senders list");
    H3 h3ChooseSender = new H3("Choose sender");

    public SenderListView(SenderService senderService) {
        this.senderService = senderService;

        createPageView();
    }

    private void createPageView(){
        var grdMain = createGrid();
        List<Sender> senders = getAllSenders();
        var dialog = createDialog();

        if(senders.size()>0)
            grdMain.setItems(senders);
        else
            dialog.open();


        grdMain.addSelectionListener(selection -> {
            Optional<Sender> optionalSender = selection.getFirstSelectedItem();
            var pathToNavigate = "packs/"+ optionalSender.get().getId();
            this.getUI().ifPresent(ui->ui.navigate(pathToNavigate));
        });

        add(
                h1SendersList,
                h3ChooseSender,
                grdMain
        );
    }
    private Grid createGrid(){
        Grid<Sender> grid = new Grid<>(Sender.class, false);
        grid.addColumn(Sender::getEmail).setHeader("Email");
        grid.addColumn(Sender::getPhoneNumber).setHeader("Phone Number");
        grid.addColumn(Sender::getPacksAmount).setHeader("Number of packs");

        return grid;
    }
    private List<Sender> getAllSenders(){
        var senders = senderService.getAllSenders();
        return senders;
    }
    private Dialog createDialog(){
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nie ma nadawców w systemie.");
        Button button = new Button("Ok");
        dialog.getFooter().add(button);
        button.addClickListener(e->{
            dialog.close();
        });
        return dialog;
    }
}
