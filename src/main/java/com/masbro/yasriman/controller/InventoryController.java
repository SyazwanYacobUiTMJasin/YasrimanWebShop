package com.masbro.yasriman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.masbro.yasriman.dao.AccountDAO;
import com.masbro.yasriman.dao.InventoryDAO;
import com.masbro.yasriman.model.Inventory;
import com.masbro.yasriman.model.Plant;
import com.masbro.yasriman.model.Tool;
import com.masbro.yasriman.model.accounts;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryDAO inventoryDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public InventoryController(InventoryDAO inventoryDAO, AccountDAO accountDAO) {
        this.inventoryDAO = inventoryDAO;
        this.accountDAO = accountDAO;
    }

    @GetMapping
    public String handleGetAction(@RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "id", required = false) Integer id,
            Model model, HttpSession session) throws SQLException {
        if (action == null) {
            // Default action when no action is specified
            return listInventory(model, session);
        }

        switch (action) {
            case "view":
                return viewInventory(id, model, session);
            case "edit":
                return showEditForm(id, model, session);
            case "new":
                return showNewForm(model, session);
            case "viewallproducts":
                return displayAllProducts(model, session);
            case "viewplantproducts":
                return displayPlantProducts(model, session);
            case "viewtoolproducts":
                return displayToolProducts(model, session);
            case "viewproductdetails":
                return viewProductDetailsById(id, model, session);
            default:
                return listInventory(model, session);
        }
    }

    @PostMapping
    public String handlePostAction(@RequestParam(value = "action", required = false) String action,
            @RequestParam("name") String inventoryName,
            @RequestParam("pricePerItem") double inventoryPricePerItem,
            @RequestParam("desc") String inventoryDesc,
            @RequestParam("status") String inventoryStatus,
            @RequestParam("role") String inventoryRole,
            @RequestParam("quantityIn") int inventoryQuantityIn,
            @RequestParam("image") MultipartFile file,
            @RequestParam(value = "plantManual", required = false) String plantManual,
            @RequestParam(value = "toolCategory", required = false) String toolCategory,
            @RequestParam(value = "id", required = false) Integer id,
            HttpSession session) throws IOException, SQLException {
        if ("insert".equals(action)) {
            return insertInventory(inventoryName, inventoryPricePerItem, inventoryDesc, inventoryStatus,
                    inventoryRole, inventoryQuantityIn, file, plantManual, toolCategory, session);
        } else if ("update".equals(action)) {
            return updateInventory(id, inventoryName, inventoryPricePerItem, inventoryDesc, inventoryStatus,
                    inventoryRole, inventoryQuantityIn, file, plantManual, toolCategory, session);
        } else {
            return "redirect:/inventory";
        }
    }

    private String listInventory(Model model, HttpSession session) throws SQLException {
        List<Inventory> inventoryList = inventoryDAO.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        addSessionAttributes(model, session);
        return "inventory";
    }

    private String viewInventory(int inventoryID, Model model, HttpSession session) throws SQLException {
        Inventory inventory = inventoryDAO.selectInventory(inventoryID);
        if (inventory == null) {
            model.addAttribute("errorMessage", "Inventory not found");
            return "error";
        }
        model.addAttribute("inventory", inventory);
        if (inventory instanceof Plant) {
            model.addAttribute("plantManual", ((Plant) inventory).getPlantManual());
        } else if (inventory instanceof Tool) {
            model.addAttribute("toolCategory", ((Tool) inventory).getToolCategory());
        }
        addSessionAttributes(model, session);
        return "viewinventory";
    }

    private String showEditForm(int inventoryID, Model model, HttpSession session) throws SQLException {
        Inventory existingInventory = inventoryDAO.selectInventory(inventoryID);
        accounts account = accountDAO.getAccountById(existingInventory.getAccountID());
        model.addAttribute("inventory", existingInventory);
        model.addAttribute("account", account);
        if (existingInventory instanceof Plant) {
            model.addAttribute("plantManual", ((Plant) existingInventory).getPlantManual());
        } else if (existingInventory instanceof Tool) {
            model.addAttribute("toolCategory", ((Tool) existingInventory).getToolCategory());
        }
        addSessionAttributes(model, session);
        return "editinventory";
    }

    private String showNewForm(Model model, HttpSession session) {
        addSessionAttributes(model, session);
        return "newinventory";
    }

    private String displayAllProducts(Model model, HttpSession session) throws SQLException {
        List<Inventory> inventoryList = inventoryDAO.selectAllInventory();
        model.addAttribute("inventoryList", inventoryList);
        addSessionAttributes(model, session);
        return "product";
    }

    private String displayPlantProducts(Model model, HttpSession session) throws SQLException {
        List<Inventory> inventoryList = inventoryDAO.selectAllInventoryByPlant();
        model.addAttribute("inventoryList", inventoryList);
        addSessionAttributes(model, session);
        return "product";
    }

    private String displayToolProducts(Model model, HttpSession session) throws SQLException {
        List<Inventory> inventoryList = inventoryDAO.selectAllInventoryByTool();
        model.addAttribute("inventoryList", inventoryList);
        addSessionAttributes(model, session);
        return "product";
    }

    private String viewProductDetailsById(int inventoryID, Model model, HttpSession session) throws SQLException {
        Inventory inventory = inventoryDAO.selectInventory(inventoryID);
        if (inventory == null) {
            model.addAttribute("errorMessage", "Inventory not found");
            return "error";
        }
        model.addAttribute("inventory", inventory);
        if (inventory instanceof Plant) {
            model.addAttribute("plantManual", ((Plant) inventory).getPlantManual());
        } else if (inventory instanceof Tool) {
            model.addAttribute("toolCategory", ((Tool) inventory).getToolCategory());
        }
        addSessionAttributes(model, session);
        return "productdetails";
    }

    private String insertInventory(String inventoryName, double inventoryPricePerItem, String inventoryDesc,
            String inventoryStatus, String inventoryRole, int inventoryQuantityIn,
            MultipartFile file, String plantManual, String toolCategory,
            HttpSession session) throws IOException, SQLException {
        int accountID = (int) session.getAttribute("loggedinaccountid");

        byte[] inventoryImage = null;
        if (!file.isEmpty()) {
            inventoryImage = file.getBytes();
        }

        Inventory inventory;
        if ("plant".equals(inventoryRole)) {
            inventory = new Plant();
            ((Plant) inventory).setPlantManual(plantManual);
        } else if ("tool".equals(inventoryRole)) {
            inventory = new Tool();
            ((Tool) inventory).setToolCategory(toolCategory);
        } else {
            inventory = new Inventory();
        }

        inventory.setInventoryName(inventoryName);
        inventory.setInventoryPricePerItem(inventoryPricePerItem);
        inventory.setInventoryDesc(inventoryDesc);
        inventory.setInventoryStatus(inventoryStatus);
        inventory.setInventoryImage(inventoryImage);
        inventory.setInventoryRole(inventoryRole);
        inventory.setInventoryQuantityIn(inventoryQuantityIn);
        inventory.setInventoryQuantityExisting(inventoryQuantityIn); // Initial quantity
        inventory.setAccountID(accountID);
        inventory.setInvmanageDateChanged(new Date());

        inventoryDAO.insertInventory(inventory);
        return "redirect:/inventory";
    }

    private String updateInventory(int inventoryID, String inventoryName, double inventoryPricePerItem,
            String inventoryDesc, String inventoryStatus, String inventoryRole,
            int inventoryQuantityIn, MultipartFile file, String plantManual,
            String toolCategory, HttpSession session) throws IOException, SQLException {
        int accountID = (int) session.getAttribute("loggedinaccountid");

        Inventory inventory = inventoryDAO.selectInventory(inventoryID);
        if (inventory == null) {
            // Handle the case where inventory is not found
            return "redirect:/inventory";
        }

        inventory.setInventoryName(inventoryName);
        inventory.setInventoryPricePerItem(inventoryPricePerItem);
        inventory.setInventoryDesc(inventoryDesc);
        inventory.setInventoryStatus(inventoryStatus);
        inventory.setInventoryRole(inventoryRole);
        inventory.setInventoryQuantityIn(inventoryQuantityIn);
        inventory.setAccountID(accountID);
        inventory.setInvmanageDateChanged(new Date());

        if (!file.isEmpty()) {
            inventory.setInventoryImage(file.getBytes());
        }

        int existingQuantity = inventory.getInventoryQuantityExisting();
        inventory.setInventoryQuantityExisting(existingQuantity + inventoryQuantityIn);

        if (inventory instanceof Plant) {
            ((Plant) inventory).setPlantManual(plantManual);
        } else if (inventory instanceof Tool) {
            ((Tool) inventory).setToolCategory(toolCategory);
        }

        inventoryDAO.updateInventory(inventory);
        return "redirect:/inventory?action=view&id=" + inventoryID;
    }

    private void addSessionAttributes(Model model, HttpSession session) {
        model.addAttribute("loggedinaccountid", session.getAttribute("loggedinaccountid"));
        model.addAttribute("accountrole", session.getAttribute("accountrole"));
    }

    @ExceptionHandler({ SQLException.class, IOException.class })
    public ModelAndView handleException(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorMessage", ex.getMessage());
        System.out.println(ex.getMessage());
        return model;
    }
}