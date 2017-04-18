package opt.gen.alg.domain.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import opt.gen.alg.domain.GADataEntry;

@Entity(name = "V_PICK_LOCATIONS")
public final class PickLocationViewDO implements Serializable, GADataEntry<Long, String> {

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "X")
	private Double x;

	@Column(name = "Y")
	private Double y;

	@Column(name = "LINE")
	private String line;

	@Column(name = "SCANCODE")
	private String scanCode;

	@Column(name = "DELIVERYID")
	private Long deliveryId;

	@Column(name = "CUSTOMERID")
	private Long customerId;

	@Column(name = "TRX")
	private Long trx;

	@Column(name = "COSEQ")
	private Long coSeq;

	@Column(name = "CLIENTID")
	private Long clientId;

	@Column(name = "WRIN")
	private Long wrin;

	@Column(name = "QUANTITY")
	private BigDecimal quantity;

	@Column(name = "GOODSID")
	private Long goodsId;

	@Column(name = "WAREHOUSEID")
	private String warehouseId;

	@Column(name = "LOCATION")
	private String location;

	@Column(name = "PICKSEQ")
	private Long pickSeq;

	@Column(name = "PICKZONEID")
	private String pickZoneId;

	@Column(name = "GRWEIGHT")
	private BigDecimal grWeight;

	@Column(name = "CBM")
	private BigDecimal cbm;

	@Column(name = "REFILLEVENT")
	private Integer refillEvent;

	@Column(name = "QUANTITYBASE")
	private BigDecimal quantityBase;

	@Column(name = "PSLIPTYPEID")
	private Integer pslipTypeId;

	@Column(name = "DPACK")
	private String dPack;

    public PickLocationViewDO() {
    }

    public PickLocationViewDO(final Long customerId) {
        this.customerId = customerId;
    }

	public PickLocationViewDO(final Long customerId, final String pickZoneId) {
		this.customerId = customerId;
		this.pickZoneId = pickZoneId;
	}

	@Override
    public Long getOptimizationParameter() {
        return getCustomerId();
    }

    @Override
    public String getGroupingParameter() {
        return getLocation();
    }

	@Override
	public Double getCoordinateX() {
		return getX();
	}

	@Override
	public Double getCoordinateY() {
		return getY();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getTrx() {
		return trx;
	}

	public void setTrx(Long trx) {
		this.trx = trx;
	}

	public Long getCoSeq() {
		return coSeq;
	}

	public void setCoSeq(Long coSeq) {
		this.coSeq = coSeq;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getWrin() {
		return wrin;
	}

	public void setWrin(Long wrin) {
		this.wrin = wrin;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getPickSeq() {
		return pickSeq;
	}

	public void setPickSeq(Long pickSeq) {
		this.pickSeq = pickSeq;
	}

	public String getPickZoneId() {
		return pickZoneId;
	}

	public void setPickZoneId(String pickZoneId) {
		this.pickZoneId = pickZoneId;
	}

	public BigDecimal getGrWeight() {
		return grWeight;
	}

	public void setGrWeight(BigDecimal grWeight) {
		this.grWeight = grWeight;
	}

	public BigDecimal getCbm() {
		return cbm;
	}

	public void setCbm(BigDecimal cbm) {
		this.cbm = cbm;
	}

	public Integer getRefillEvent() {
		return refillEvent;
	}

	public void setRefillEvent(Integer refillEvent) {
		this.refillEvent = refillEvent;
	}

	public BigDecimal getQuantityBase() {
		return quantityBase;
	}

	public void setQuantityBase(BigDecimal quantityBase) {
		this.quantityBase = quantityBase;
	}

	public Integer getPslipTypeId() {
		return pslipTypeId;
	}

	public void setPslipTypeId(Integer pslipTypeId) {
		this.pslipTypeId = pslipTypeId;
	}

	public String getdPack() {
		return dPack;
	}

	public void setdPack(String dPack) {
		this.dPack = dPack;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PickLocationViewDO that = (PickLocationViewDO)o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;
		if (x != null ? !x.equals(that.x) : that.x != null)
			return false;
		if (y != null ? !y.equals(that.y) : that.y != null)
			return false;
		if (line != null ? !line.equals(that.line) : that.line != null)
			return false;
		if (scanCode != null ? !scanCode.equals(that.scanCode) : that.scanCode != null)
			return false;
		if (deliveryId != null ? !deliveryId.equals(that.deliveryId) : that.deliveryId != null)
			return false;
		if (customerId != null ? !customerId.equals(that.customerId) : that.customerId != null)
			return false;
		if (trx != null ? !trx.equals(that.trx) : that.trx != null)
			return false;
		if (coSeq != null ? !coSeq.equals(that.coSeq) : that.coSeq != null)
			return false;
		if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null)
			return false;
		if (wrin != null ? !wrin.equals(that.wrin) : that.wrin != null)
			return false;
		if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null)
			return false;
		if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null)
			return false;
		if (warehouseId != null ? !warehouseId.equals(that.warehouseId) : that.warehouseId != null)
			return false;
		if (location != null ? !location.equals(that.location) : that.location != null)
			return false;
		if (pickSeq != null ? !pickSeq.equals(that.pickSeq) : that.pickSeq != null)
			return false;
		if (pickZoneId != null ? !pickZoneId.equals(that.pickZoneId) : that.pickZoneId != null)
			return false;
		if (grWeight != null ? !grWeight.equals(that.grWeight) : that.grWeight != null)
			return false;
		if (cbm != null ? !cbm.equals(that.cbm) : that.cbm != null)
			return false;
		if (refillEvent != null ? !refillEvent.equals(that.refillEvent) : that.refillEvent != null)
			return false;
		if (quantityBase != null ? !quantityBase.equals(that.quantityBase) : that.quantityBase != null)
			return false;
		if (pslipTypeId != null ? !pslipTypeId.equals(that.pslipTypeId) : that.pslipTypeId != null)
			return false;
		return dPack != null ? dPack.equals(that.dPack) : that.dPack == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (x != null ? x.hashCode() : 0);
		result = 31 * result + (y != null ? y.hashCode() : 0);
		result = 31 * result + (line != null ? line.hashCode() : 0);
		result = 31 * result + (scanCode != null ? scanCode.hashCode() : 0);
		result = 31 * result + (deliveryId != null ? deliveryId.hashCode() : 0);
		result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
		result = 31 * result + (trx != null ? trx.hashCode() : 0);
		result = 31 * result + (coSeq != null ? coSeq.hashCode() : 0);
		result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
		result = 31 * result + (wrin != null ? wrin.hashCode() : 0);
		result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
		result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
		result = 31 * result + (warehouseId != null ? warehouseId.hashCode() : 0);
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (pickSeq != null ? pickSeq.hashCode() : 0);
		result = 31 * result + (pickZoneId != null ? pickZoneId.hashCode() : 0);
		result = 31 * result + (grWeight != null ? grWeight.hashCode() : 0);
		result = 31 * result + (cbm != null ? cbm.hashCode() : 0);
		result = 31 * result + (refillEvent != null ? refillEvent.hashCode() : 0);
		result = 31 * result + (quantityBase != null ? quantityBase.hashCode() : 0);
		result = 31 * result + (pslipTypeId != null ? pslipTypeId.hashCode() : 0);
		result = 31 * result + (dPack != null ? dPack.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "PickLocationViewDO {" + "id=" + id + ", x=" + x + ", y=" + y + ", line='" + line + '\'' + ", scanCode='" + scanCode + '\'' + ", deliveryId="
			+ deliveryId + ", customerId=" + customerId + ", trx=" + trx + ", coSeq=" + coSeq + ", clientId=" + clientId + ", wrin=" + wrin + ", quantity="
			+ quantity + ", goodsId=" + goodsId + ", warehouseId='" + warehouseId + '\'' + ", location='" + location + '\'' + ", pickSeq=" + pickSeq
			+ ", pickZoneId='" + pickZoneId + '\'' + ", grWeight=" + grWeight + ", cbm=" + cbm + ", refillEvent=" + refillEvent + ", quantityBase="
			+ quantityBase + ", pslipTypeId=" + pslipTypeId + ", dPack='" + dPack + '\'' + '}';
	}
}