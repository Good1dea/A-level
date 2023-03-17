package com.sydoruk.servlet;

import com.sydoruk.model.Type;
import com.sydoruk.service.CarService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletCarCreate", value = "/createCar")
public class ServletCarCreate extends HttpServlet {

    @Override
    public void init() {
        System.out.println(getServletName() + " initialized");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("Type");
        Type inputType = Type.valueOf(type);
        CarService.getInstance().createCar(inputType);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(getServletName() + " destroyed");
    }

}