export class OrderHistory {

    constructor(
    public id: string,
    public orderTrackingNumber: string,
    public totalPrice: number,
    public dateCreated: Date,
    public totalQuantity: number
    ) {

    }
}
