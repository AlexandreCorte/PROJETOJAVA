package ggc.core;

//FIXME import classes (cannot import from pt.tecnico or ggc.app)
// INVALID DATE, NOSUCHPRODUCT, NOSUCHPARTNER, DUPLICATEPARTNER;
import java.io.*;
import java.util.*;

import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.BadEntryException;
import ggc.core.exception.ImportFileException;
import ggc.core.exception.UnavailableFileException;
import ggc.core.exception.MissingFileAssociationException;

/** Façade for access. */
public class WarehouseManager {

  /** Name of file storing current warehouse. */
  private String _filename = "";

  /** The wharehouse itself. */
  private Warehouse _warehouse = new Warehouse();

  // Warehouse
  public Warehouse getWarehouse() {

    return _warehouse;
  }

  // Date

  public Date getDate() {
    return _warehouse.getDate();
  }

  public void advanceDate(int days) throws InvalidDate{
    _warehouse.advanceDate(days);
  }

  public int displayDate() {
    return _warehouse.displayDate();
  }

  // Partner

  public Collection<Acquisition> printAcquisitions(String partnerId) throws UnknownPartnerKey {
    return _warehouse.printAcquisitions(partnerId);
  }

  public Collection<Sale> printSales(String partnerId) throws UnknownPartnerKey {
    return _warehouse.printSales(partnerId);
  }

  Collection<Partner> getPartners() {
    return _warehouse.getPartners();
  }

  public Partner getPartner(String id) {

    return _warehouse.getPartner(id);

  }

  public void checkPartner(String id) throws UnknownPartnerKey {
    _warehouse.checkPartner(id);
  }

  public void registerPartner(String id, String name, String adress) throws DuplicatePartnerKey {
    _warehouse.registerPartner(id, name, adress);
  }

  public Collection<Partner> printPartners() {
    return _warehouse.printPartners();
  }

  public Partner printPartner(String id) throws UnknownPartnerKey {
    return _warehouse.printPartner(id);
  }

  // Balance

  public double getAvailableBalance() {
    return _warehouse.getAvailableBalance();
  }

  public double getAccountingBalance() {
    return _warehouse.getAccountingBalance();
  }

  // Product

  public void checkTotalStock(String productId, int quantity) throws UnavailableProduct {
    _warehouse.checkTotalStock(productId, quantity);
  }

  public boolean checkProduct(String id) {
    return _warehouse.checkProduct(id);
  }

  public Collection<Product> printProducts() {
    return _warehouse.printProducts();
  }

  public void productExists(String id) throws UnknownProductKey {
    _warehouse.productExists(id);
  }

  public int getProductStock(String productId) {
    return _warehouse.getProductStock(productId);
  }

  // Batch
  public Collection<Batch> printBatches() {
    return _warehouse.printBatches();
  }

  public Collection<Batch> printBatchesByPartner(String id) throws UnknownPartnerKey {
    return _warehouse.printBatchesByPartner(id);
  }

  public Collection<Batch> printBatchesByProduct(String id) throws UnknownProductKey {
    return _warehouse.printBatchesByProduct(id);
  }

  public Collection<Batch> getBatchesUnderPrice(double price) {
    return _warehouse.getBatchesUnderPrice(price);
  }

  public void registerSimpleProduct(String id) {
    _warehouse.registerSimpleProduct(id);
  }

  public void registerAggregateProduct(String id, Recipe recipe) {
    _warehouse.registerAggregateProduct(id, recipe);
  }

  public Product getProduct(String id) {
    return _warehouse.getProduct(id);
  }

  // Transaction

  public void registerAcquisitionTransaction(String partnerId, String productId, double price, int amount, int flag) {
    _warehouse.registerAcquisitionTransaction(partnerId, productId, price, amount, flag);
  }

  public void registerSaleTransaction(String partnerId, String productId, int deadline, int amount)
      throws UnavailableProduct, UnavailableProductException {
    _warehouse.registerSaleTransaction(partnerId, productId, deadline, amount);
  }

  public Transaction printTransaction(int id) throws UnknownTransactionKey {
    return _warehouse.printTransaction(id);
  }

  public Transaction getTransaction(int id){
    return _warehouse.getTransaction(id);
  }

  public void receivePayment(Transaction transaction){
    _warehouse.receivePayment(transaction);
  }

  public void checkTransaction(int id) throws UnknownTransactionKey{
    _warehouse.checkTransaction(id);
  }

  public void setPartnerPoints(String partnerId, double points) {
    _warehouse.setPartnerPoints(partnerId, points);

  }

  public void updateAmountPaidbyPartner(double amountPaid, String partnerId) {
    _warehouse.updateAmountPaidbyPartner(amountPaid, partnerId);
  }

  public void registerBreakDownTransaction(String productId, String partnerId, int amount) {
    _warehouse.registerBreakDownTransaction(productId, partnerId, amount);
  }

  // Notifications

  public void updateNotifications(String productId) {
    _warehouse.updateNotifications(productId);
  }

  public void turnOnOfNotifications(String productId, String partnerId)
      throws UnknownProductKey, UnknownPartnerKey {
    _warehouse.turnOnOfNotifications(productId, partnerId);
  }

  public Collection<Notification> showPartnerNotifications(String partnerId) {
    return _warehouse.showPartnerNotifications(partnerId);
  }

  public void clearNotifications(String partnerId) {
    _warehouse.clearNotifications(partnerId);
  }

  /**
   * @@throws IOException
   * @@throws FileNotFoundException
   * @@throws MissingFileAssociationException
   */
  public String getFilename() {

    return _filename;

  }

  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    if ("".equals(_filename)) {
      throw new FileNotFoundException();
    }
    try (ObjectOutputStream obOut = new ObjectOutputStream(new FileOutputStream(_filename))) {
      obOut.writeObject(_warehouse);
    }
  }

  /**
   * @@param filename
   * @@throws MissingFileAssociationException
   * @@throws IOException
   * @@throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @@param filename
   * @@throws UnavailableFileException
   */
  public void load(String filename) throws UnavailableFileException {
    try (ObjectInputStream obIN = new ObjectInputStream(new FileInputStream(filename))) {
      Object obj = obIN.readObject();
      _warehouse = (Warehouse) obj;
    } catch (ClassNotFoundException | IOException e) {
      throw new UnavailableFileException(filename);
    }
  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _warehouse.importFile(textfile);
    } catch (IOException | BadEntryException | DuplicatePartnerKey/* FIXME maybe other exceptions */ e) {
      throw new ImportFileException(textfile, e);
    }
  }

  public void setFileName(String filename) {
    _filename = filename;
  }

}
