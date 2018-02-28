/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.fit.rentalservice.webservices;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import semestralka.fit.rentalservice.Clients;
import semestralka.fit.rentalservice.ClientsList;

/**
 *
 * @author bekbolot
 */
@Stateless
@Path("semestralka.fit.rentalservice.clients")
public class ClientsFacadeREST extends AbstractFacade<Clients> {

    @PersistenceContext(unitName = "semestralka.fit_RentalService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ClientsFacadeREST() {
        super(Clients.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(Clients entity) {
        List<Clients> listClients;
        listClients = em.createQuery("SELECT c FROM Clients c WHERE c.name = :custName AND c.surename = :custSureName")
                           .setParameter("custName", entity.getName())
                            .setParameter("custSureName",entity.getSurename())
                            .getResultList();
        
        if( listClients.isEmpty() )
        {
            em.persist(entity);
            em.flush();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Clients entity) {
        Clients p = em.find(Clients.class,id);
        p.setName(entity.getName());
        p.setSurename(entity.getSurename());
        p.setTelNumber(entity.getTelNumber());
        p.setRentedFlat(entity.getRentedFlat());
        em.merge(p);
        em.flush();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Clients p = em.find(Clients.class, id);
        em.remove(p);
        em.flush();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Clients find(@PathParam("id") Long id) {
        return em.find(Clients.class, id);
    }
    
    
    @GET
    @Path("findAllClients")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findAllClients(){
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c FROM Clients c").getResultList());
        return clist;
    }
    
    
    @GET
    @Path("findByName/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByName(@PathParam("name")String name) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c FROM Clients c WHERE c.name = :custName")
                         .setParameter("custName", name)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findByNameSurename/{name}/{surename}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByNameSurename(@PathParam("name")String name, @PathParam("surename")String surename) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE c.name = :custName AND c.surename = :custSur")
                         .setParameter("custName", name)
                         .setParameter("custSur", surename)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findByNameTelNumber/{name}/{telNum}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByNameTelNumber(@PathParam("name")String name, @PathParam("telNum")String telNumber) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE c.name = :custName AND c.telNumber = :custSur")
                         .setParameter("custName", name)
                         .setParameter("custSur", telNumber)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findByNameTelNumberSurename/{name}/{surenmae}/{telNum}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByNameTelNumberSurename(@PathParam("name")String name, 
                                                    @PathParam("telNum")String telNumber, 
                                                    @PathParam("surename")String surename) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE c.name = :custName AND c.surename = :custSur AND c.telNumber :tel")
                         .setParameter("custName", name)
                         .setParameter("custSur", surename)
                         .setParameter("tel", telNumber)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findByTel/{telNum}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByTel(@PathParam("telNum")String tel) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE c.telNumber = :tel")
                         .setParameter("tel", tel)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findBySurename/{surename}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findBySurename(@PathParam("surename")String surename) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE c.surename = :sur")
                         .setParameter("sur", surename)
                         .getResultList());
        return clist;
    }
    
    @GET
    @Path("findByTelNumberSurename/{surename}/{telNum}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ClientsList findByTelNumberSurename(@PathParam("telNum")String telNumber, 
                                                @PathParam("surename")String surename) {
        ClientsList clist = new ClientsList();
        clist.setClients(em.createQuery("SELECT c    "
                +       "FROM Clients c "
                +       "WHERE c.surename = :surename AND c.telNumber = :tel")
                         .setParameter("surename", surename)
                         .setParameter("tel", telNumber)
                         .getResultList());
        return clist;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Clients> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return em.createQuery("SELECT c "
                +       "FROM Clients c "
                +       "WHERE :from <= c.id AND c.id <= :to")
                         .setParameter("from", from)
                         .setParameter("to", to)
                         .getResultList();
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return  em.createQuery("SELECT COUNT(c) FROM Clients c").toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
