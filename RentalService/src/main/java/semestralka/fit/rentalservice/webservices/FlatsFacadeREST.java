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
import javax.ws.rs.core.MediaType;
import semestralka.fit.rentalservice.Clients;
import semestralka.fit.rentalservice.Flats;
import semestralka.fit.rentalservice.FlatsList;

/**
 *
 * @author bekbolot
 */
@Stateless
@Path("semestralka.fit.rentalservice.flats")
public class FlatsFacadeREST extends AbstractFacade<Flats> {

    @PersistenceContext(unitName = "semestralka.fit_RentalService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public FlatsFacadeREST() {
        super(Flats.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(Flats entity) {
        List<Flats> listFlats;
        listFlats = em.createQuery("SELECT c FROM Flats c WHERE c.address = :address")
                    .setParameter("address", entity.getAddress())
                    .getResultList();
        
        if( listFlats.isEmpty() )
        {
            em.persist(entity);
            em.flush();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Flats entity) {
        Flats p = em.find(Flats.class,id);
        p.setAddress(entity.getAddress());
        p.setPricePerMonth(entity.getPricePerMonth());
        p.setService(entity.getService());
        
        em.merge(p);
        em.flush();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        Flats p = em.find(Flats.class, id);
        em.remove(p);
        em.flush();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Flats find(@PathParam("id") Long id) {
        return em.find(Flats.class, id);
    }
    
    @GET
    @Path("findByAddress/{addr}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FlatsList findByAddress(@PathParam("addr") String address) {
        FlatsList flist = new FlatsList();
        
        flist.setFlats(em.createQuery("SELECT c FROM Flats c WHERE c.address = :custAd")
                         .setParameter("custAd", address)
                         .getResultList());
        return flist;
    }

    @GET
    @Path("findAllFlats")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FlatsList findAllFlats() {
        FlatsList flist = new FlatsList();
        flist.setFlats(em.createQuery("SELECT c FROM Flats c").getResultList());
        return flist;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public FlatsList findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        FlatsList flist = new FlatsList();
        
        flist.setFlats( em.createQuery("SELECT c "
                +       "FROM Flats c "
                +       "WHERE :from <= c.id AND c.id <= :to")
                         .setParameter("from", from)
                         .setParameter("to", to)
                         .getResultList());
        return flist;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return  em.createQuery("SELECT COUNT(c) FROM Flats c").toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
