package com.example.transportApp.view;

import com.example.transportApp.model.Sender;
import com.example.transportApp.service.SenderService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
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
        var senders = getAllSenders();
        var dialog = createDialog();
        GridListDataView<Sender> dataView = null;

        if(senders.size()>0)
            dataView = grdMain.setItems(senders);
        else
            dialog.open();

        var searchFld = createFilter(senders,dataView);

        grdMain.addSelectionListener(selection -> {
            Optional<Sender> optionalSender = selection.getFirstSelectedItem();
            var pathToNavigate = "packs/"+ optionalSender.get().getId();
            this.getUI().ifPresent(ui->ui.navigate(pathToNavigate));
        });

        add(
                h1SendersList,
                h3ChooseSender,
                searchFld,
                grdMain
        );
    }
    private Grid<Sender> createGrid(){
        Grid<Sender> grid = new Grid<>(Sender.class, false);
        grid.addColumn(Sender::getEmail).setHeader("Email");
        grid.addColumn(Sender::getPhoneNumber).setHeader("Phone Number");
        grid.addColumn(Sender::getPacksAmount).setHeader("Number of packs");

        return grid;
    }
    private TextField createFilter(List<Sender> senders, GridListDataView<Sender> dataView){

        TextField searchField = new TextField();
        searchField.setWidth("20%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(sender -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesPhoneNum = matchesTerm(sender.getPhoneNumber(),
                    searchTerm);
            boolean matchesEmail = matchesTerm(sender.getEmail(), searchTerm);

            return matchesPhoneNum || matchesEmail;
        });
        return searchField;
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
    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
