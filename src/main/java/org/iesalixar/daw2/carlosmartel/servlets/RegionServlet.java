package org.iesalixar.daw2.carlosmartel.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.carlosmartel.daos.RegionDAO;
import org.iesalixar.daw2.carlosmartel.daos.RegionDAOImpl;
import org.iesalixar.daw2.carlosmartel.entities.Region;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/regions")
public class RegionServlet extends HttpServlet {
    private RegionDAO regionDAO;

    @Override
    public void init() throws ServletException {
        try {
            regionDAO = new RegionDAOImpl();
        } catch (Exception e) {
            throw new RuntimeException("Error al incializar el RegionDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            if(action == null) {
                action = "list";
            }
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                default:
                    listRegions(request,response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Region existingRegion = regionDAO.getRegionById(id);
        request.setAttribute("region", existingRegion);
        request.getRequestDispatcher("views/region/region-form.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertRegion(request, response);
                    break;
                case "update":
                    updateRegion(request, response);
                    break;
                default:
                    listRegions(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void updateRegion(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code").trim().toUpperCase();
        String name = request.getParameter("name").trim();

        if (code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos.");
            request.getRequestDispatcher("views/region/region-form.jsp").forward(request, response);
            return;
        }

        if (regionDAO.existsRegionByCodeAndNotId(code, id)) {
            request.setAttribute("errorMessage", "El código de la región ya existe para otra región.");
            request.getRequestDispatcher("views/region/region-form.jsp").forward(request, response);
            return;
        }

        Region updatedRegion = new Region(id, code, name);
        try {
            regionDAO.updateRegion(updatedRegion);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                request.setAttribute("errorMessage", "El código de la región debe ser único.");
                request.getRequestDispatcher("views/region/region-form.jsp").forward(request, response);
            } else {
                throw e;
            }
        }

        response.sendRedirect("regions");
    }

    private void listRegions(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
        List<Region> listregions = regionDAO.listAllRegions();
        request.setAttribute("listRegions", listregions);
        request.getRequestDispatcher("views/region/region-list.jsp").forward(request, response);
    }


    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("views/region/region-form.jsp").forward(request, response);
    }




    private void insertRegion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String code = request.getParameter("code").trim().toUpperCase();
        String name = request.getParameter("name").trim();

        if(code.isEmpty() || name.isEmpty()) {
            request.setAttribute("errorMessage", "El código y el nombre no pueden estar vacíos");
            request.getRequestDispatcher("/views/region/region-form.jsp").forward(request, response);
            return;
        }

        if(regionDAO.existRegionByCode(code)) {
            request.setAttribute("errorMessage", "El código de la región ya existe.");
            request.getRequestDispatcher("/views/region/region-form.jsp").forward(request,response);
            return;
        }

        Region newRegion = new Region(code, name);

        try {
            regionDAO.insertRegion(newRegion);
        } catch (SQLException e) {
            if(e.getSQLState().equals("23505")) {
                request.setAttribute("errorMessage", "El código de la región debe ser único");
                request.getRequestDispatcher("views/region/region-form.jsp").forward(request, response);
            } else {
                throw e;
            }
        }



    }
}
