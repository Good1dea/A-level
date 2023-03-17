package com.sydoruk.servlet;

import com.sydoruk.model.Car;
import com.sydoruk.service.CarService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowAllServlet", value = "/showAllCars")
public class ServletShowAllCars extends HttpServlet {

    @Override
    public void init() {
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> cars = CarService.getInstance().getAll();
        request.setAttribute("cars", cars);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("show-all.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }
}