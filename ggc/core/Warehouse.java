package ggc.core;

// FIXME import classes (cannot import from pt.tecnico or ggc.app)

import java.io.Serializable;
import java.io.IOException;
import java.util.*;

import ggc.app.exception.UnavailableProductException;
import ggc.core.exception.BadEntryException;

/**
 * Class Warehouse implements a warehouse.
 */
public class Warehouse implements Serializable {

  private Map<String, Partner> _partners = new HashMap<>();
  private Map<String, Product> _products = new HashMap<>();
  private Date _date = new Date();
  private Map<Integer, Transaction> _transactions = new HashMap<Integer, Transaction>();
  private int _transactionId;

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202109192006L;

  // Date
  public Date getDate() {

    return _date;
  }

  int displayDate() {
    return _date.getDays();
  }

  void advanceDate(int days) throws InvalidDate {
    List<Transaction> transactions = new ArrayList<Transaction>(getTransactions());
    if (days <= 0) {
      throw new InvalidDate();
    }
    _date.addDays(days);
    for (Transaction t : transactions) {
      if (!t.isPaid()) {
        t.setAmountToPay(getPriceSale(t));
      }
    }
  }

  // Partner

  Collection<Acquisition> printAcquisitions(String partnerId) throws UnknownPartnerKey {
    if (!_partners.containsKey(partnerId.toLowerCase())) {
      throw new UnknownPartnerKey();
    }
    List<Acquisition> acquisitions = new ArrayList<Acquisition>(_partners.get(partnerId.toLowerCase()).getAcquisitions());
    acquisitions.sort(new ComparatorTransactionById());
    return acquisitions;
  }

  Collection<Sale> printSales(String partnerId) throws UnknownPartnerKey {
    if (!_partners.containsKey(partnerId.toLowerCase())) {
      throw new UnknownPartnerKey();
    }
    List<Sale> sales = new ArrayList<Sale>(_partners.get(partnerId.toLowerCase()).getSales());
    sales.sort(new ComparatorTransactionById());
    return sales;
  }

  Partner getPartner(String id) {

    return _partners.get(id.toLowerCase());
  }

  void checkPartner(String id) throws UnknownPartnerKey {
    if (!_partners.containsKey(id.toLowerCase())) {
      throw new UnknownPartnerKey();
    }
  }

  Collection<Partner> getPartners() {
    return _partners.values();
  }

  void addPartnerInterested(String id) {
    List<Product> productsList = new ArrayList<>(getProducts());
    for (Product p : productsList) {
      p.addObserver(_partners.get(id.toLowerCase()));
    }
  }

  void registerPartner(String id, String name, String address) throws DuplicatePartnerKey {
    if (_partners.containsKey(id.toLowerCase())) {
      throw new DuplicatePartnerKey();
    }
    Partner partner = new Partner(name, id, address);
    _partners.put(id.toLowerCase(), partner);
    addPartnerInterested(id);
  }

  Collection<Partner> printPartners() {
    List<Partner> partnersList = new ArrayList<>(getPartners());
    partnersList.sort(new ComparatorById());
    return partnersList;
  }

  Partner printPartner(String id) throws UnknownPartnerKey {
    if (!_partners.containsKey(id.toLowerCase())) {
      throw new UnknownPartnerKey();
    }
    return _partners.get(id.toLowerCase());
  }

  void updateAmountPaidbyPartner(double amountPaid, String partnerId) {
    _partners.get(partnerId.toLowerCase()).updatePaidValue(amountPaid);

  }

  // Product

  Product getProduct(String id) {

    return _products.get(id.toLowerCase());
  }

  void checkTotalStock(String productId, int quantity) throws UnavailableProduct {
    int totalStock = _products.get(productId.toLowerCase()).getTotalStock();
    if (totalStock < quantity) {
      throw new UnavailableProduct();
    }
  }

  void registerSimpleProduct(String id) {
    if (!_products.containsKey(id.toLowerCase())) {
      _products.put(id.toLowerCase(), new SimpleProduct(id));
    }
  }

  void registerAggregateProduct(String id, Recipe recipe) {
    if (!_products.containsKey(id.toLowerCase())) {
      _products.put(id.toLowerCase(), new AggregateProduct(id, recipe));
    }
  }

  Recipe createRecipe(List<Product> products, List<Integer> quantities, double alpha) {
    int size = products.size();
    Product product;
    int quantity;
    List<Component> components = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      product = products.get(i);
      quantity = quantities.get(i);
      components.add(new Component(quantity, product));
    }
    return new Recipe(alpha, components);
  }

  Collection<Product> getProducts() {
    return _products.values();
  }

  Collection<Product> printProducts() {
    List<Product> productsList = new ArrayList<>(getProducts());
    productsList.sort(new ComparatorProducts());
    return productsList;
  }

  boolean checkProduct(String id) {
    return _products.containsKey(id.toLowerCase());
  }

  void productExists(String id) throws UnknownProductKey {
    if (!_products.containsKey(id.toLowerCase())) {
      throw new UnknownProductKey();
    }
  }

  int getProductStock(String productId) {
    return _products.get(productId.toLowerCase()).getTotalStock();
  }

  // Batch

  Collection<Batch> getBatchesByPartner() {
    List<Partner> partnersList = new ArrayList<>(getPartners());
    List<Batch> batches = new ArrayList<>();
    for (Partner p : partnersList) {
      List<Batch> partnerBatches = new ArrayList<Batch>(p.getBatches());
      batches.addAll(partnerBatches);
    }
    return batches;
  }

  Collection<Batch> getBatchesByProduct() {
    List<Product> productsList = new ArrayList<>(getProducts());
    List<Batch> batches = new ArrayList<>();
    for (Product p : productsList) {
      List<Batch> productBatches = new ArrayList<Batch>(p.getBatches());
      batches.addAll(productBatches);
    }
    return batches;
  }

  void updateMaxPrice(double price, String productId) {
    if (_products.get(productId.toLowerCase()).getMaxPrice() == 0) {
      _products.get(productId.toLowerCase()).setMaxPrice(price);
    }
    if (_products.get(productId.toLowerCase()).getMaxPrice() < price) {
      _products.get(productId.toLowerCase()).setMaxPrice(price);
    }
  }

  void createBatch(double price, int stock, String partnerId, String productId) {
    Product product = _products.get(productId.toLowerCase());
    Partner partner = _partners.get(partnerId.toLowerCase());
    Batch batch = new Batch(price, stock, partner, product);
    _products.get(productId.toLowerCase()).getBatches().add(batch);
    _partners.get(partnerId.toLowerCase()).getBatches().add(batch);
    _products.get(productId.toLowerCase()).addStock(stock);
    updateMaxPrice(price, productId);
  }

  Collection<Batch> printBatches() {
    List<Batch> batches = new ArrayList<Batch>(getBatchesByPartner());
    batches.sort(new ComparatorBatches());
    return batches;
  }

  Collection<Batch> printBatchesByPartner(String id) throws UnknownPartnerKey {
    if (!_partners.containsKey(id.toLowerCase())) {
      throw new UnknownPartnerKey();
    }
    Partner partner = _partners.get(id.toLowerCase());
    List<Batch> batches = new ArrayList<Batch>(partner.getBatches());
    batches.sort(new ComparatorBatchesByPartner());
    return batches;
  }

  Collection<Batch> printBatchesByProduct(String id) throws UnknownProductKey {
    if (!_products.containsKey(id.toLowerCase())) {
      throw new UnknownProductKey();
    }
    Product product = _products.get(id.toLowerCase());
    List<Batch> batches = new ArrayList<Batch>(product.getBatches());
    batches.sort(new ComparatorBatchesByProduct());
    return batches;
  }

  Collection<Batch> getBatchesUnderPrice(double price) {
    List<Batch> batches = new ArrayList<Batch>(getBatchesByPartner());
    batches.sort(new ComparatorBatches());
    return batches;
  }

  // Transaction

  void receivePayment(Transaction transaction) {
    String partnerId = transaction.getPartnerId();
    Partner partner = _partners.get(partnerId.toLowerCase());
    String productId = transaction.getProductId();
    Date deadline = transaction.getDeadline();
    Date currentDate = new Date(getDate().getDays());
    int N = _products.get(productId.toLowerCase()).getLimitDays();
    double amountToPay = 0;
    double points = 0;
    double partnerPoints = partner.getPoints();
    if (transaction.isPaid())
      return;

    if (deadline != null && !transaction.isPaid()) {
      if ((deadline.difference(currentDate) >= N)) {
        amountToPay = getPriceSale(transaction);
        points = partner.getStatus().updatePointsP1(amountToPay, partnerPoints, partner);
      }

      else if (deadline.difference(currentDate) >= 0 && deadline.difference(currentDate) < N) {
        amountToPay = getPriceSale(transaction);
        points = partner.getStatus().updatePointsP2(amountToPay, partnerPoints, partner);
      }

      else if (currentDate.difference(deadline) > 0 && currentDate.difference(deadline) <= N) {
        amountToPay = getPriceSale(transaction);
        points = partner.getStatus().updatePointsP3(amountToPay, currentDate, deadline, partnerPoints, partner);
      }

      else if (currentDate.difference(deadline) > N) {
        amountToPay = getPriceSale(transaction);
        points = partner.getStatus().updatePointsP4(amountToPay, currentDate, deadline, partnerPoints, partner);
      }
      partner.setPoints(points);
      transaction.Pay(amountToPay, currentDate);
      updateAmountPaidbyPartner(amountToPay, partnerId);
    }
  }

  Collection<Transaction> getTransactions() {
    return _transactions.values();
  }

  double getPriceSale(Transaction transaction) {
    String partnerId = transaction.getPartnerId();
    Partner partner = _partners.get(partnerId.toLowerCase());
    String productId = transaction.getProductId();
    double baseValue = transaction.getBaseValue();
    Date deadline = transaction.getDeadline();
    Date currentDate = new Date(getDate().getDays());
    int N = _products.get(productId.toLowerCase()).getLimitDays();
    double amountToPay = 0;

    if (deadline != null && !transaction.isPaid()) {
      if ((deadline.difference(currentDate) >= N)) {
        amountToPay = partner.getStatus().PaymentP1(currentDate, deadline, baseValue);
      }

      else if (deadline.difference(currentDate) >= 0 && deadline.difference(currentDate) < N) {
        amountToPay = partner.getStatus().PaymentP2(currentDate, deadline, baseValue);
      }

      else if (currentDate.difference(deadline) > 0 && currentDate.difference(deadline) <= N) {
        amountToPay = partner.getStatus().PaymentP3(currentDate, deadline, baseValue);
      }

      else if (currentDate.difference(deadline) > N) {
        amountToPay = partner.getStatus().PaymentP4(currentDate, deadline, baseValue);
      }
    }

    return amountToPay;
  }

  void setPartnerPoints(String partnerId, double points) {
    getPartner(partnerId.toLowerCase()).setPoints(points);

  }

  double updateBatchQuantities(List<Batch> batches, int amount, String productId, String partnerId) {
    int currentAmount= 0, quantityLeft = 0, batchQuantity = 0, batchNumber = 0, size = batches.size();
    double price = 0;
    Batch batch;
    while (batchNumber != size) {

      while (currentAmount != amount) {
        quantityLeft = amount - currentAmount;
        batchQuantity = batches.get(batchNumber).getQuantity();
        String partnerToRemove = batches.get(batchNumber).getPartnerId();

        if (batchQuantity > quantityLeft) {
          currentAmount = amount;
          batches.get(batchNumber).updateQuantity(-quantityLeft);
          price += quantityLeft * batches.get(batchNumber).getPrice();
          _products.get(productId.toLowerCase()).addStock(-quantityLeft);
          batchNumber = size;
          break;
        } 

        else {
          currentAmount += batchQuantity;
          price += batches.get(batchNumber).getPrice() * batchQuantity;
          batch = batches.get(batchNumber);
          _products.get(productId.toLowerCase()).getBatches().remove(batch);
          _partners.get(partnerToRemove.toLowerCase()).getBatches().remove(batch);
          _products.get(productId.toLowerCase()).addStock(-batchQuantity);
          batchNumber += 1;
          if (quantityLeft==batchQuantity)
            batchNumber=size;
        }
      }
    }
    return price;
  }

  Transaction printTransaction(int id) throws UnknownTransactionKey {
    if (!_transactions.containsKey(id)) {
      throw new UnknownTransactionKey();
    }
    return _transactions.get(id);
  }

  Transaction getTransaction(int id) {
    return _transactions.get(id);
  }

  void checkTransaction(int transactionId) throws UnknownTransactionKey {
    if (!_transactions.containsKey(transactionId)) {
      throw new UnknownTransactionKey();
    }
  }

  double aggregateProduct(List<Component> components, double alpha, double price, String partnerId, String productId){
    for (Component c: components){
      Product product = c.getProduct();
        int quantity = product.getTotalStock();
        int neededQuantity = c.getQuantity();

        if (neededQuantity>quantity && product.getRecipe()!=null){
          int neededQuantityToAggregate=neededQuantity-quantity;
          List<Component> componentsNewAggregate = new ArrayList<Component>(product.getRecipe().getComponents());
          double alphaNewAggregate = product.getRecipe().getAlpha();
          
          for (int i =0; i!=neededQuantityToAggregate; i++){
          double priceToAggregate=0;
          priceToAggregate = aggregateProduct(componentsNewAggregate, alphaNewAggregate, priceToAggregate, partnerId, productId);
          price += priceToAggregate;
          updateMaxPrice(priceToAggregate, product.getId());
          }

          List<Batch> batches = new ArrayList<Batch>(product.getBatches());
          batches.sort(new ComparatorBatchesByPrice());
          price += updateBatchQuantities(batches, quantity, product.getId(), partnerId);
        }

        if (neededQuantity<=quantity){
            List<Batch> batches = new ArrayList<Batch>(product.getBatches());
            batches.sort(new ComparatorBatchesByPrice());
            price += updateBatchQuantities(batches, neededQuantity, product.getId(), partnerId);
        }
    }
    updateMaxPrice(price*(1+alpha), productId);
    return price*(1+alpha);
  }

  void checkQuantityToAggregate(List<Component> components, int askedAmount, double alpha) throws UnavailableProductException{
      for (Component c: components){
        Product product = c.getProduct();
        int quantity = product.getTotalStock();
        int neededQuantity = askedAmount*c.getQuantity();

        if (neededQuantity>quantity && product.getRecipe()==null){
          throw new UnavailableProductException(product.getId(), neededQuantity, quantity);
        }

        if (neededQuantity>quantity && product.getRecipe()!=null){
          List<Component> componentsNewAggregate = new ArrayList<Component>(product.getRecipe().getComponents());
          double alphaNewAggregate = product.getRecipe().getAlpha();
          int neededQuantityNewAggregate = neededQuantity-quantity;
          checkQuantityToAggregate(componentsNewAggregate, neededQuantityNewAggregate, alphaNewAggregate);
        }
      }
  }

  void registerAggregation(String productId, int askedAmount, String partnerId, Date paymentDate, Date deadLineDate, int totalAmount) throws UnavailableProductException{
    Product product = _products.get(productId.toLowerCase());
    Recipe recipe = product.getRecipe();
    List<Component> components = new ArrayList<Component>(recipe.getComponents());
    double alpha = recipe.getAlpha();
    double price =0;
    double totalPrice = 0;

    checkQuantityToAggregate(components, askedAmount, alpha);
    for(int i=0; i!=askedAmount;i++){
      totalPrice+=aggregateProduct(components, alpha, price, partnerId, productId);
    }

    List<Batch> batches = new ArrayList<Batch>(_products.get(productId.toLowerCase()).getBatches());
    batches.sort(new ComparatorBatchesByPrice());
    double sellPrice = updateBatchQuantities(batches, product.getTotalStock(), productId, partnerId);

    double finalPrice = sellPrice + totalPrice;
    Sale sale = new SaleByCredit(_transactionId, finalPrice, totalAmount, paymentDate, productId, partnerId, deadLineDate, finalPrice);
    _transactions.put(_transactionId, sale);
    _partners.get(partnerId.toLowerCase()).getSales().add(sale);
    _transactionId++;
    _partners.get(partnerId.toLowerCase()).addSellValue(finalPrice);
  }

  void registerSaleTransaction(String partnerId, String productId, int deadline, int amount) throws UnavailableProductException, UnavailableProduct {
    
    if (amount == 0) {
      return;
    }

    Date deadLineDate = new Date(deadline);
    Date paymentDate = new Date(-2);
    int availableAmount = _products.get(productId.toLowerCase()).getTotalStock();
    
    if (availableAmount < amount && _products.get(productId.toLowerCase()).getRecipe()!=null) {
      int amountToAggregate = amount - availableAmount;
      registerAggregation(productId, amountToAggregate, partnerId, paymentDate, deadLineDate, amount);
      return;
    }

    if (availableAmount<amount && _products.get(productId.toLowerCase()).getRecipe()==null){
      throw new UnavailableProduct();
    }

    List<Batch> batches = new ArrayList<Batch>(_products.get(productId.toLowerCase()).getBatches());
    batches.sort(new ComparatorBatchesByPrice());
    double price = updateBatchQuantities(batches, amount, productId, partnerId);
    Sale sale = new SaleByCredit(_transactionId, price, amount, paymentDate, productId, partnerId, deadLineDate, price);
    double amountToPay = getPriceSale(sale);
    Sale validSale = new SaleByCredit(_transactionId, price, amount, paymentDate, productId, partnerId, deadLineDate, amountToPay);
    _transactions.put(_transactionId, validSale);
    _partners.get(partnerId.toLowerCase()).getSales().add(validSale);
    _transactionId++;
    _partners.get(partnerId.toLowerCase()).addSellValue(price);
  }

  void registerAcquisitionAuxiliar(Acquisition acquisition, double price, int amount, String partnerId,
      String productId) {
    _transactions.put(_transactionId, acquisition);
    _partners.get(partnerId.toLowerCase()).getAcquisitions().add(acquisition);
    _transactionId++;
    _partners.get(partnerId.toLowerCase()).addBuyValue(price);
  }

  double getComponentPrice(Product product) {
    double price;
    List<Batch> batches = new ArrayList<Batch>(product.getBatches());
    if (batches.isEmpty()) {
      price = product.getMaxPrice();
    } else {
      batches.sort(new ComparatorBatchesByPrice());
      price = batches.get(0).getPrice();
    }
    return price;
  }

  void registerAcquisitionTransaction(String partnerId, String productId, double price, int amount, int flag) {
    if (amount == 0) {
      return;
    }
    Date date = new Date(getDate().getDays());
    String productName = _products.get(productId.toLowerCase()).getId();
    String partnerName = _partners.get(partnerId.toLowerCase()).getId();
    double totalStock = _products.get(productId.toLowerCase()).getTotalStock();
    double paidPrice = price * amount;
    
    if (flag == 1 && totalStock != 0 && verifyNotification(productId, price)) {
      createBargainNotification(productName, price);
    }
   
    Acquisition acquisition = new Acquisition(_transactionId, paidPrice, amount, date, productName, partnerName);
    registerAcquisitionAuxiliar(acquisition, paidPrice, amount, partnerId, productId);
    createBatch(price, amount, partnerName, productName);
    
    if (flag == 1 && totalStock == 0) {
      createNewNotification(productName, price);
    }

  }

  BreakdownInfo updateComponentsQuantities(String productId, int amount, String partnerName, String productName) {
    double price = 0, priceByProduct = 0;
    List<Product> products = new ArrayList<Product>();
    List<Integer> quantities = new ArrayList<Integer>();
    List<Double> prices = new ArrayList<Double>();
    int componentQuantity = 0, amountToAdd = 0;
    Recipe recipe = _products.get(productId.toLowerCase()).getRecipe();
    List<Component> components = new ArrayList<Component>(recipe.getComponents());
    
    for (Component c : components) {
      componentQuantity = c.getQuantity();
      Product product = c.getProduct();
      priceByProduct = getComponentPrice(product);
      amountToAdd = componentQuantity * amount;
      createBatch(priceByProduct, amountToAdd, partnerName, product.getId());
      price += priceByProduct * amountToAdd;
      products.add(product);
      quantities.add(amountToAdd);
      prices.add(priceByProduct * amountToAdd);
    }

    BreakdownInfo info = new BreakdownInfo(products, quantities, prices, price);
    return info;
  }

  void registerBreakDownTransactionAuxiliar(double totalPrice, int amount, Date paymentDate, String productName,
    String partnerName, String partnerId, List<Product> products, List<Integer> quantities, List<Double> prices) {
    double paidPrice = 0;

    if (totalPrice < 0)
      paidPrice = 0;

    else
      paidPrice = totalPrice;

    BreakDown breakDown = new BreakDown(_transactionId, totalPrice, amount, paymentDate, productName, partnerName,
        paidPrice, products, quantities, prices);
    _transactions.put(_transactionId, breakDown);
    _partners.get(partnerId.toLowerCase()).getSales().add(breakDown);
    _transactionId++;
    _partners.get(partnerId.toLowerCase()).updatePoints(paidPrice * 10);
  }

  void registerBreakDownTransaction(String productId, String partnerId, int amount) {

    if (amount == 0) {
      return;
    }

    if (_products.get(productId.toLowerCase()).getRecipe()==null)
      return;

    String productName = _products.get(productId.toLowerCase()).getId();
    String partnerName = _partners.get(partnerId.toLowerCase()).getId();
    Date paymentDate = new Date(getDate().getDays());
    List<Batch> batches = new ArrayList<Batch>(_products.get(productId.toLowerCase()).getBatches());
    batches.sort(new ComparatorBatchesByPrice());

    double sellPrice = updateBatchQuantities(batches, amount, productId, partnerId);
    BreakdownInfo info = updateComponentsQuantities(productId, amount, partnerName, productName);

    double buyPrice = info.getPrice();
    List<Product> products = new ArrayList<Product>(info.getProducts());
    List<Integer> quantities = new ArrayList<Integer>(info.getQuantities());
    List<Double> prices = new ArrayList<Double>(info.getPrices());

    double totalPrice = sellPrice - buyPrice;
    registerBreakDownTransactionAuxiliar(totalPrice, amount, paymentDate, productName, partnerName, partnerId, products,
        quantities, prices);
  }

  // Balance
  double getAvailableBalance() {
    double availableBalance = 0;
    List<Transaction> transactions = new ArrayList<Transaction>(getTransactions());
    for (Transaction t : transactions) {
      if (t.isPaid()) {
        availableBalance += t.updatePrice();
      }
    }
    return availableBalance;
  }

  double getAccountingBalance() {
    double accountingBalance = 0;
    List<Transaction> transactions = new ArrayList<Transaction>(getTransactions());
    for (Transaction t : transactions) {
      if (t.isPaid()) {
        accountingBalance += t.updatePrice();
      } else {
        accountingBalance += getPriceSale(t);
      }
    }
    return accountingBalance;
  }

  // Notifications

  boolean verifyNotification(String productId, double price){
    Product product = _products.get(productId.toLowerCase());
    List<Batch> batches = new ArrayList<Batch>(product.getBatches());
    if (batches.isEmpty()){
      return false;
    }
    else{
      batches.sort(new ComparatorBatchesByPrice());
      return batches.get(0).getPrice()>price;
    }
  }

  void createNewNotification(String productId, double price) {
    Product product = _products.get(productId.toLowerCase());
    product.update("NEW", price);
  }

  void createBargainNotification(String productId, double price) {
    Product product = _products.get(productId.toLowerCase());
    product.update("BARGAIN", price);
  }

  void updateNotifications(String idProduct) {
    Product product = _products.get(idProduct.toLowerCase());
    List<Partner> partnersList = new ArrayList<Partner>(getPartners());
    for (Partner p : partnersList) {
      product.addObserver(p);
    }
  }

  void turnOnOfNotifications(String productId, String partnerId) throws UnknownProductKey, UnknownPartnerKey {
    if (!_partners.containsKey(partnerId.toLowerCase())) {
      throw new UnknownPartnerKey();
    }

    Partner partner = _partners.get(partnerId.toLowerCase());
    if (!_products.containsKey(productId.toLowerCase())) {
      throw new UnknownProductKey();
    }
    
    Collection<Observer> observers = _products.get(productId.toLowerCase()).getObservers();
    if (observers.contains(partner)) {
      observers.remove(partner);
    } else {
      observers.add(partner);
    }
  }

  Collection<Notification> showPartnerNotifications(String partnerId) {
    return _partners.get(partnerId.toLowerCase()).getNotifications();
  }

  void clearNotifications(String partnerId) {
    _partners.get(partnerId.toLowerCase()).getNotifications().clear();
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile)
      throws IOException, BadEntryException, DuplicatePartnerKey /* FIXME maybe other exceptions */ {
    Parser parse = new Parser(this);
    parse.parseFile(txtfile);
  }

}
