export interface IInventoryTransactions {  
	createdAt?: Date;
	id: number;
	quantity: number;
	transactionType: string;

	productsDescriptiveField?: number;
	productId?: number;
}


