package com.example.transportApp.view;

import com.example.transportApp.exceptions.ItemNotFoundException;
import com.example.transportApp.model.Pack;
import com.example.transportApp.service.PackService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;

@Route("packs/discount/:idPaczki")
@PageTitle("Discount page")
public class EditView extends VerticalLayout implements BeforeEnterObserver {
    private Integer idPaczki;
    Binder<Pack> binder = new Binder<>(Pack.class);
    PackService service;
    String pathToPacks="";
    @SneakyThrows
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        event.getRouteParameters().getInteger("idPaczki")
                .ifPresent(value -> idPaczki = value);
        addComponents();

    }
    public EditView(PackService service) throws ItemNotFoundException {
        this.service = service;
    }
    private void addComponents() throws ItemNotFoundException {
        var pack = service.getPackById((long) idPaczki);
        var strPackNumber = "Paczka nr "+pack.getNrPaczki();
        var strPackPrice = "Cena: "+ pack.getCena();
        Label lblPackNumber = new Label(strPackNumber);
        Label lblPackPrice = new Label(strPackPrice);

        var discountField = createField();
        var btnReturn = createButton("Return");
        var btnSave = createButton("Save");

        var strPageTitle = "Give a discount";
        pathToPacks = "packs/"+pack.getSender().getId();
        initBinder(discountField);

        btnReturn.addClickListener(e->
                this.getUI().ifPresent(ui->
                        ui.navigate(pathToPacks)
                )
        );
        btnSave.addClickListener(e->
                {
                    try {
                        setDiscount(pack,discountField);
                    } catch (ItemNotFoundException packNotFoundException) {
                        packNotFoundException.printStackTrace();
                    }
                }
                );

        add(
                new H1(strPageTitle),
                lblPackNumber,
                lblPackPrice,
                discountField,
                new HorizontalLayout(btnReturn,btnSave)
        );
    }
    private void setDiscount(Pack pack, NumberField discount) throws ItemNotFoundException {
        if(binder.isValid()){
            var cena = pack.getCena();
            var discountValue = discount.getValue();
            var nowaCena = cena*(discountValue/100);

            cena -= nowaCena;
            pack.setCena(cena);
            service.updatePack(pack, pack.getId());
            addAnswer(pack,discountValue);
        }
    }
    private void addAnswer(Pack pack, Double discountValue){
        Dialog dialog = new Dialog();
        var strAnswer = "Czy napewno chcesz wprowadzic znizke o "+discountValue+" procent?";
        dialog.setHeaderTitle(strAnswer);
        Button yesButton = new Button("Tak");
        Button noButton = new Button("Nie");
        yesButton.addClickListener(e->{
                dialog.close();
                addDialog(pack, discountValue);
        }
        );
        noButton.addClickListener(e->{
                dialog.close();
        });

        dialog.getFooter().add(noButton);
        dialog.getFooter().add(yesButton);
        dialog.open();

    }
    private void addDialog(Pack pack, Double discountValue){
        Dialog dialog = new Dialog();
        var strDiscountGiven = "Wprowadzono zniżke o "+discountValue+" procent!";
        dialog.setHeaderTitle(strDiscountGiven);
        VerticalLayout dialogLayout = createDialogLayout(pack);
        dialog.add(dialogLayout);
        Button okButton = new Button("OK");
        dialog.getFooter().add(okButton);
        okButton.addClickListener(e->{
                dialog.close();
                this.getUI().get().navigate(pathToPacks);
                }
                );
        dialog.open();

    }
    private static VerticalLayout createDialogLayout(Pack pack) {
        var strPackNumber = "Paczka nr "+pack.getNrPaczki();
        var strPackNewPrice = "Nowa cena: "+pack.getCena();
        Label packNumber = new Label(strPackNumber);
        Label packNewPrice = new Label(strPackNewPrice);

        VerticalLayout dialogLayout = new VerticalLayout(packNumber,
                packNewPrice);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }
    private void initBinder(NumberField discount){
        binder.forField(discount)
                .withValidator(
                        name -> name >= 0 && name<=100,
                        "Discount must be between 0 and 100.")
                .bind(Pack::getCena, Pack::setCena);
    }
    private NumberField createField(){
        NumberField discountField = new NumberField();
        discountField.setRequiredIndicatorVisible(true);
        discountField.setLabel("Discount");
        discountField.setPlaceholder("%");
        return discountField;
    }
    private Button createButton(String btnName){
        Button button = new Button(btnName);
        return button;
    }

}
