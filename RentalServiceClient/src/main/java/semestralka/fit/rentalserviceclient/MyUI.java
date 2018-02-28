package semestralka.fit.rentalserviceclient;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import semestralka.fit.rentalserviceclient.EntityClasses.Clients;
import semestralka.fit.rentalserviceclient.EntityClasses.ClientsList;
import semestralka.fit.rentalserviceclient.EntityClasses.Flats;
import semestralka.fit.rentalserviceclient.EntityClasses.FlatsList;
import semestralka.fit.rentalserviceclient.EntityClasses.Services;
import semestralka.fit.rentalserviceclient.EntityClasses.ServicesList;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    
    ClientsFacade client = new ClientsFacade();
    FlatsFacade     flats = new FlatsFacade();
    ServicesFacade  services = new ServicesFacade();
    
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
        final VerticalLayout content = new VerticalLayout();
        
        TabSheet tabsheet = new TabSheet();
        
        setupClientsLayout(tabsheet);
        setupFlatsLayout(tabsheet);
        setupServicesLayout(tabsheet);
        
        content.addComponent(tabsheet);
        setContent(content);
        
    }
    
    private void setupFlatsLayout(TabSheet tabsheet) {
        final HorizontalLayout flatsLayout = new HorizontalLayout();
        tabsheet.addTab(flatsLayout).setCaption("Flats");
        
        Grid<Flats> grid = new Grid<>();
        
        setupCreateFlatLayout(flatsLayout);
        setupAllFlatsLayout( flatsLayout, grid);
        setupEditFlatsLayout( flatsLayout, grid);
        
    }
    
    private void setupEditFlatsLayout(HorizontalLayout flatsLayout, Grid<Flats> grid){
        
        final VerticalLayout editFlatsLayout = new VerticalLayout();
        
        final TextField id = new TextField();
        id.setCaption("ID");
        
        final TextField addressEdit = new TextField();
        addressEdit.setCaption("Address");
        
        final TextField priceEdit = new TextField();
        priceEdit.setCaption("Price / Month");
        
        final TextArea text = new TextArea("Status");
        text.setWordWrap(true);
        
        ComboBox combobox = new ComboBox("Select One");
        
        combobox.setItems(getServices( services.findAllServices_JSON(ServicesList.class)).toArray() );
        
        
        Button searchButton = new Button("Search");
            searchButton.addClickListener(e -> {
                
                if( ! id.getValue().isEmpty())
                {
                    grid.setItems(flats.findRange_JSON(Flats.class,id.getValue(), id.getValue()));
                    text.setValue("Successful search");
                }
                else if( ! addressEdit.getValue().isEmpty() )
                {
                    grid.setItems(flats.findByAddress_JSON(FlatsList.class, addressEdit.getValue()).getFlats());
                    text.setValue("Successful search");
                }
            });
        
        
        
        Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to delete flat");
                }
                else if( flats.find_JSON(Flats.class, id.getValue()) == null )
                {
                    text.setValue("Flat with given id doesn't exist!");
                }
                else
                {
                    flats.remove(id.getValue());
                    text.setValue("Successfully deleted");
                }
            });
        
        Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to edit flat");
                }
                else if( flats.find_JSON(Flats.class, id.getValue()) == null )
                {
                    text.setValue("Flat with given id doesn't exist!");
                }
                else
                {
                    Flats p = flats.find_JSON(Flats.class, id.getValue());
                    
                    if( ! addressEdit.getValue().isEmpty() )
                        p.setAddress(addressEdit.getValue());
                    if( ! priceEdit.getValue().isEmpty() )
                        p.setPricePerMonth( Long.parseLong(priceEdit.getValue()) );
                    
                    if( !  combobox.isEmpty() )
                    {
                        ServicesList flist = services.findByName_JSON(ServicesList.class, combobox.getValue().toString());
                        Services f = services.find_JSON(Services.class, flist.getServices().get(0).getId().toString() );
                        p.setService(f);
                    }
                    
                    flats.edit_JSON(p, id.getValue() );
                    
                    text.setValue("Successfully edited");
                }
            });
        
        editFlatsLayout.addComponents(id,addressEdit,priceEdit,combobox, searchButton,deleteButton,editButton,text);
        
        flatsLayout.addComponent(editFlatsLayout);
    }
    
    
    List<String> getServices(ServicesList f)
    {
        List<Services> fl = f.getServices();
        
        List<String> allServices = new ArrayList<String>();
        
        for( int i=0; i< fl.size(); i++ )
        {
           allServices.add(fl.get(i).getName());
        }
        
        return allServices;
    }
    
    List<String> getAddress(FlatsList f)
    {
        List<Flats> fl = f.getFlats();
        
        List<String> allFlats = new ArrayList<String>();
        
        for( int i=0; i< fl.size(); i++ )
        {
           allFlats.add(fl.get(i).getAddress());
        }
        
        return allFlats;
    }
    
    private void setupAllFlatsLayout(HorizontalLayout flatsLayout, Grid<Flats> grid){
        final VerticalLayout allFlatLayout = new VerticalLayout();
        
        Button showFlatsButton = new Button("Show all flats");
            showFlatsButton.addClickListener(e -> {
                FlatsList list = flats.findAllFlats_JSON(FlatsList.class);
                grid.setItems(list.getFlats());
            });
        
        FlatsList clist = flats.findAllFlats_JSON(FlatsList.class);
        grid.setItems(clist.getFlats());
        grid.setSizeUndefined();
        grid.addColumn(Flats::getId).setCaption("Id");
        grid.addColumn(Flats::getAddress).setCaption("Address");
        grid.addColumn(Flats::getPricePerMonth).setCaption("Price / Month");
        grid.addColumn(Flats::getServiceName).setCaption("Service");
        
        allFlatLayout.addComponents(showFlatsButton,grid);
        flatsLayout.addComponent(allFlatLayout);
    }
    
    private void setupCreateFlatLayout(HorizontalLayout flatsLayout){
        final VerticalLayout createFlatLayout = new VerticalLayout();
        
        final TextField address = new TextField();
        address.setCaption("Type address here:");
        
        final TextField price = new TextField();
        price.setCaption("Type price here:");
        
        Button createButton = new Button("Create Flat");
            createButton.addClickListener(e -> {
                Flats p = new Flats();
                p.setAddress(address.getValue());
                p.setPricePerMonth( Long.parseLong(price.getValue()));
                flats.create_JSON(p);
            }); 
            
        createFlatLayout.addComponents(address, price, createButton);
        
        flatsLayout.addComponent(createFlatLayout);
    }
    
    private void setupServicesLayout(TabSheet tabsheet) {
        final HorizontalLayout servicesLayout = new HorizontalLayout();
        tabsheet.addTab(servicesLayout).setCaption("Services");
        
        Grid<Services> grid = new Grid<>();
        
        setupCreateServicesLayout(servicesLayout);
        setupAllServicesLayout( servicesLayout, grid);
        setupEditServicesLayout( servicesLayout, grid);
    }
    
    private void setupCreateServicesLayout(HorizontalLayout clientsLayout) {
        
        final VerticalLayout createServicesLayout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Name of the service:");
        
        final TextField description = new TextField();
        description.setCaption("Description of the service:");

        final TextField price = new TextField();
        price.setCaption("Price here:");
        
        
        Button createButton = new Button("Create Service");
            createButton.addClickListener(e -> {
                Services p = new Services();
                p.setName(name.getValue());
                p.setDescription(description.getValue());
                p.setPricePerMonth(Long.parseLong(price.getValue()) );
                services.create_JSON(p);
            }); 
        createServicesLayout.addComponents(name,description,price,createButton);
        
        clientsLayout.addComponent(createServicesLayout);
    }
    
    private void setupAllServicesLayout(HorizontalLayout servicesLayout, Grid<Services> grid) {
        final VerticalLayout allServicesLayout = new VerticalLayout();
        
        ServicesList clist = new ServicesList();
        clist.setServices(services.findAllServices_JSON(ServicesList.class).getServices());
        grid.setItems(clist.getServices());
        grid.setSizeUndefined();
        grid.addColumn(Services::getId).setCaption("Id");
        grid.addColumn(Services::getName).setCaption("Name");
        grid.addColumn(Services::getDescription).setCaption("Description");
        grid.addColumn(Services::getPricePerMonth).setCaption("Price").setWidth(40);
        
         Button showUserButton = new Button("Show all services");
            showUserButton.addClickListener(e -> {
                ServicesList list = services.findAllServices_JSON(ServicesList.class);
                grid.setItems(list.getServices());
            });
        
        allServicesLayout.addComponents(showUserButton,grid);
        
        servicesLayout.addComponent(allServicesLayout);
    }
    
    private void setupEditServicesLayout(HorizontalLayout servicesLayout, Grid<Services> grid) {
        final VerticalLayout editServicesLayout = new VerticalLayout();
        
        final TextField id = new TextField();
        id.setCaption("ID");
        
        final TextField nameEdit = new TextField();
        nameEdit.setCaption("NAME");
        
        final TextField descriptionEdit = new TextField();
        descriptionEdit.setCaption("DESCRIPTION");

        final TextField priceEdit = new TextField();
        priceEdit.setCaption("PRICE");
        
        final TextArea text = new TextArea("Status");
        text.setWordWrap(true);
        
        Button searchButton = new Button("Search");
            searchButton.addClickListener(e -> {
                updateGridBySearch(grid, id, nameEdit, descriptionEdit);
                text.setValue("Successful search");
            });
        
        
        
        Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to delete service");
                }
                else if( services.find_JSON(Services.class, id.getValue()) == null )
                {
                    text.setValue("Service with given id doesn't exist!");
                }
                else
                {
                    services.remove(id.getValue());
                    text.setValue("Successfully deleted");
                }
            });
        
        Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to edit service");
                }
                else if( services.find_JSON(Services.class, id.getValue()) == null )
                {
                    text.setValue("Service with given id doesn't exist!");
                }
                else
                {
                    Services p = services.find_JSON(Services.class, id.getValue());
                    
                    if( ! nameEdit.getValue().isEmpty() )
                        p.setName(nameEdit.getValue());
                    if( ! descriptionEdit.getValue().isEmpty() )
                        p.setDescription(descriptionEdit.getValue());
                    if( ! priceEdit.getValue().isEmpty() )
                        p.setPricePerMonth( Long.parseLong(priceEdit.getValue()) );
                    
                    services.edit_JSON(p, id.getValue() );
                    
                    text.setValue("Successfully edited");
                }
            });
        
        editServicesLayout.addComponents(id,nameEdit,descriptionEdit,priceEdit, searchButton,deleteButton,editButton,text);
        
        servicesLayout.addComponent(editServicesLayout);
    }
    
    private void updateGridBySearch(Grid<Services> grid, TextField id, TextField nameEdit, TextField descriptionEdit) {
        if( ! id.getValue().isEmpty() )
                    {
                        List<Services> p = Arrays.asList( services.find_JSON(Services.class,id.getValue()) );
                        grid.setItems(p);
                    }
        else if( ! nameEdit.getValue().isEmpty() )
            {
                grid.setItems(services.findByName_JSON(ServicesList.class,nameEdit.getValue()).getServices());
            }
        
    }
    
    private void setupClientsLayout(TabSheet tabsheet) {
        
        final HorizontalLayout clientsLayout = new HorizontalLayout();
        tabsheet.addTab(clientsLayout).setCaption("Clients");
        
        Grid<Clients> grid = new Grid<>();
        
        setupCreatUserLayout( clientsLayout );
        setupAllUserLayout(clientsLayout, grid );
        setupEditUserLayout( clientsLayout, grid );
    }
    
     private void setupEditUserLayout(HorizontalLayout clientsLayout, Grid<Clients> grid) {
        final VerticalLayout editUserLayout = new VerticalLayout();
        
        final TextField id = new TextField();
        id.setCaption("ID");
        
        final TextField nameEdit = new TextField();
        nameEdit.setCaption("NAME");
        
        final TextField surenameEdit = new TextField();
        surenameEdit.setCaption("SURENAME");

        final TextField telEdit = new TextField();
        telEdit.setCaption("PHONE NUMBER");
        
        final TextArea text = new TextArea("Status");
        text.setWordWrap(true);
        
        ComboBox combobox = new ComboBox("Select One");
        
        combobox.setItems(getAddress( flats.findAllFlats_JSON(FlatsList.class)).toArray() );
        
        Button searchButton = new Button("Search");
            searchButton.addClickListener(e -> {
                updateGridBySearch(grid, id, nameEdit, surenameEdit, telEdit );
                text.setValue("Successful search");
            });
        
        
        
        Button deleteButton = new Button("Delete");
            deleteButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to delete client");
                }
                else if( client.find_JSON(Clients.class, id.getValue()) == null )
                {
                    text.setValue("Client with given id doesn't exist!");
                }
                else
                {
                    client.remove(id.getValue());
                    text.setValue("Successfully deleted");
                }
            });
        
        Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if( id.getValue().isEmpty() )
                {
                    text.setValue("Write ID to edit client");
                }
                else if( client.find_JSON(Clients.class, id.getValue()) == null )
                {
                    text.setValue("Client with given id doesn't exist!");
                }
                else
                {
                    Clients p = client.find_JSON(Clients.class, id.getValue());
                    
                    if( ! nameEdit.getValue().isEmpty() )
                        p.setName(nameEdit.getValue());
                    if( ! surenameEdit.getValue().isEmpty() )
                        p.setSurename(surenameEdit.getValue());
                    if( ! telEdit.getValue().isEmpty() )
                        p.setTelNumber(telEdit.getValue());
                    if( ! combobox.isEmpty() )
                    {
                        FlatsList flist = flats.findByAddress_JSON(FlatsList.class, combobox.getValue().toString());
                        Flats f = flats.find_JSON(Flats.class, flist.getFlats().get(0).getId().toString() );
                        p.setRentedFlat(f);
                    }
                    
                        
                    System.out.println(p.getRentedFlat().getAddress());
                    
                    client.edit_JSON(p, id.getValue() );
                    
                    text.setValue("Successfully edited");
                }
            });
        
        editUserLayout.addComponents(id,nameEdit,surenameEdit,telEdit,combobox, searchButton,deleteButton,editButton,text);
        
        clientsLayout.addComponent(editUserLayout);
    }
    
    private void updateGridBySearch(Grid<Clients> grid, TextField id, TextField nameEdit, TextField surenameEdit, TextField telEdit) 
    {
        if( ! id.getValue().isEmpty() )
                    {
                        
                        
                        List<Clients> p = Arrays.asList( client.find_JSON(Clients.class,id.getValue()) );
                        
                        grid.setItems(p);
                    }
        else if( ! nameEdit.getValue().isEmpty() && 
                 ! surenameEdit.getValue().isEmpty() && 
                 ! telEdit.getValue().isEmpty() )
            {
                grid.setItems(client.findByNameTelNumberSurename_JSON(ClientsList.class, 
                                    nameEdit.getValue(),
                                    telEdit.getValue(),
                                    surenameEdit.getValue())
                                .getClients()
                            );
            }
        else if( ! nameEdit.getValue().isEmpty() && 
                 ! surenameEdit.getValue().isEmpty())
            {
               grid.setItems(client.findByNameSurename_JSON(ClientsList.class, 
                                    nameEdit.getValue(),
                                    surenameEdit.getValue())
                                .getClients()
                            ); 
            }
        else if( ! nameEdit.getValue().isEmpty() &&
                 ! telEdit.getValue().isEmpty())
            {
               grid.setItems(client.findByNameTelNumber_JSON(ClientsList.class, 
                                    nameEdit.getValue(),
                                    telEdit.getValue())
                                .getClients()
                            );  
            }
        else if( ! telEdit.getValue().isEmpty() &&
                 ! surenameEdit.getValue().isEmpty())
            {
               grid.setItems(client.findByTelNumberSurename_JSON(ClientsList.class, 
                                    telEdit.getValue(),
                                    surenameEdit.getValue())
                                .getClients()
                            );  
            }
        else if( ! nameEdit.getValue().isEmpty() )
            {
                grid.setItems(client.findByName_JSON(ClientsList.class,
                                    nameEdit.getValue())
                                .getClients()
                            );  
            }
        else if( ! surenameEdit.getValue().isEmpty() )
            {
                grid.setItems(client.findBySurename_JSON(ClientsList.class,
                                    surenameEdit.getValue())
                                .getClients()
                            );  
            }
        else if( ! telEdit.getValue().isEmpty() )
            {
                grid.setItems(client.findByTel_JSON(ClientsList.class,
                                    telEdit.getValue())
                                .getClients()
                            );
            }
    }

    private void setupCreatUserLayout(HorizontalLayout clientsLayout) {
        
        final VerticalLayout createUserLayout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");
        
        final TextField surename = new TextField();
        surename.setCaption("Type your surename here:");

        final TextField tel = new TextField();
        tel.setCaption("Type your phone number here:");
        
        
        Button createButton = new Button("Create User");
            createButton.addClickListener(e -> {
                Clients p = new Clients();
                p.setName(name.getValue());
                p.setSurename(surename.getValue());
                p.setTelNumber(tel.getValue());
                client.create_JSON(p);
            }); 
        createUserLayout.addComponents(name,surename,tel,createButton);
        
        clientsLayout.addComponent(createUserLayout);
    }

    private void setupAllUserLayout(HorizontalLayout clientsLayout, Grid<Clients> grid) {
        final VerticalLayout allUserLayout = new VerticalLayout();
        
        ClientsList clist = new ClientsList();
        clist.setClients( client.findAllClients_JSON(ClientsList.class).getClients() );
        grid.setItems(clist.getClients());
        grid.setSizeUndefined();
        grid.addColumn(Clients::getId).setCaption("Id");
        grid.addColumn(Clients::getName).setCaption("Name");
        grid.addColumn(Clients::getSurename).setCaption("Surename");
        grid.addColumn(Clients::getTelNumber).setCaption("Phone").setWidth(40);
        grid.addColumn(Clients::getRentedFlatAddress).setCaption("Flat");
        
         Button showUserButton = new Button("Show all clients");
            showUserButton.addClickListener(e -> {
                ClientsList list = client.findAllClients_JSON(ClientsList.class);
                grid.setItems(list.getClients());
            });
        
        allUserLayout.addComponents(showUserButton,grid);
        
        clientsLayout.addComponent(allUserLayout);
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
