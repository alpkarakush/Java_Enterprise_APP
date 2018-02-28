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
import semestralka.fit.rentalservice.Flats;
import semestralka.fit.rentalservice.Services;
import semestralka.fit.rentalservice.ServicesList;

/**
 *
 * @author bekbolot
 */
@Stateless
@Path("semestralka.fit.rentalservice.services")
public class ServicesFacadeREST extends AbstractFacade<Services> {

    @PersistenceContext(unitName = "semestralka.fit_RentalService_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ServicesFacadeREST() {
        super(Services.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void create(Services entity) {
        List<Services> listServices;
        listServices = em.createQuery("SELECT c FROM Services c WHERE c.name = :address AND c.description = :desc")
                    .setParameter("address", entity.getName())
                    .setParameter("desc",entity.getDescription())
                    .getResultList();
        
        if( listServices.isEmpty() )
        {
            em.persist(entity);
            em.flush();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, Services entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces( {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Services find(@PathParam("id") Long id) {
        return em.find(Services.class, id);
    }
    
    @GET
    @Path("findByName/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ServicesList findByName(@PathParam("name") String name) {
        ServicesList flist = new ServicesList();
        
        flist.setServices(em.createQuery("SELECT c FROM Services c WHERE c.name = :custAd")
                         .setParameter("custAd", name)
                         .getResultList());
        return flist;
    }

    @GET
    @Path("findAllServices")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ServicesList findAllServices() {
        ServicesList slist = new ServicesList();
        slist.setServices(em.createQuery("SELECT c FROM Services as c").getResultList());
        return slist;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ServicesList findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        ServicesList slist = new ServicesList();
        slist.setServices(em.createQuery("SELECT c FROM Services as WHERE c.id <= to AND c.id >= from").getResultList());
        return slist;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return  em.createQuery("SELECT COUNT(c) FROM Services c").toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
