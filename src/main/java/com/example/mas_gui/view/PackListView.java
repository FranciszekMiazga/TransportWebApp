package com.example.mas_gui.view;

import com.example.mas_gui.exceptions.ItemNotFoundException;
import com.example.mas_gui.model.*;
import com.example.mas_gui.service.SenderService;
import com.example.mas_gui.service.PackService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;

@Route("packs/:senderId")
@PageTitle("Packs list")
public class PackListView extends Div implements BeforeEnterObserver {
    PackService packService;
    SenderService senderService;
    private Integer senderId;
    H1 h1PackList = new H1("Pack list");
    H3 h3PackChoose = new H3("Choose pack to give a discount");
    String pathToMainPage = "";

    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().getInteger("senderId")
                .ifPresent(value -> senderId = value);
        createPageView();
    }
    public PackListView(PackService packService, SenderService senderService){
        this.packService = packService;
        this.senderService = senderService;
    }
    private void createPageView() throws ItemNotFoundException {
        var grdMain = createGrid();
        var client = getClient();
        var packs = client.getPacks();
        grdMain = addPacksToGrid(grdMain, packs);
        var btnReturn = addButton("Return");

        var strPackDescription = "The packs of the sender: "+client.getEmail()+" ,"+client.getPhoneNumber();
        Label lblPackDescription = new Label(strPackDescription);
        var layout = new VerticalLayout(
                h1PackList,
                h3PackChoose,
                lblPackDescription,
                grdMain,
                btnReturn
        );
        add(
            layout
        );
    }
    private Sender getClient() throws ItemNotFoundException {
        var individualClient = senderService.getSenderById((long)senderId);
        return individualClient;
    }
    private Grid<Pack> createGrid(){
        Grid<Pack> grdMain = new Grid<>(Pack.class, false);
        var strPackNumber = "Nr paczki";
        var strPrice = "Cena";
        var strStatus = "Status";
        grdMain.addColumn(Pack::getNrPaczki).setHeader(strPackNumber).setAutoWidth(true);
        grdMain.addColumn(Pack::getCena).setHeader(strPrice).setAutoWidth(true);
        grdMain.addComponentColumn(pack ->
                        createStatusBadge(pack.getStatus().toString()))
                .setHeader(strStatus).setAutoWidth(true);
        return grdMain;
    }
    private Span createStatusBadge(String status) {
        String theme;
        switch (status) {
            case "nadana":
                theme = "badge primary";
                break;
            case "anulowana":
                theme = "badge error primary";
                break;
            case "przypisana":
                theme = "badge primary";
                break;
            case "odebrana":
                theme = "badge success primary";
                break;
            default:
                theme = "badge contrast primary";
                break;
        }
        Span badge = new Span(status);
        badge.getElement().getThemeList().add(theme);
        return badge;
    }
    private Grid<Pack> addPacksToGrid(Grid grid, List<Pack> packs) {
        var dialog = createDialog();

        if(packs.size()>0)
            grid.setItems(packs);
        else
            dialog.open();

        grid.addSelectionListener(selection -> {
            Optional<Pack> optionalPack = selection.getFirstSelectedItem();
            var pathToClickedPack = "packs/discount/"+optionalPack.get().getId();

            if (optionalPack.isPresent()) {
                this.getUI().get().navigate(pathToClickedPack);
            }
        });

        return grid;
    }
    private Dialog createDialog(){
        Dialog dialog = new Dialog();
        var headerTitle = "Ten nadawca nie posiada żadnych paczek.";
        var strClickOk = "Kliknij ok, aby wybrać innego nadawce.";

        dialog.setHeaderTitle(headerTitle);
        Label lblClickOk = new Label(strClickOk);
        Button btnOk = new Button("Ok");
        dialog.add(lblClickOk);
        dialog.getFooter().add(btnOk);

        btnOk.addClickListener(e->{
            dialog.close();
            getUI().get().navigate(pathToMainPage);
        });

        return dialog;
    }
    private Button addButton(String btnName){
        Button btn = new Button(btnName);
        btn.addClickListener(e->
                this.getUI().ifPresent(ui->
                        ui.navigate(pathToMainPage)
                )
        );
        return btn;
    }

}
