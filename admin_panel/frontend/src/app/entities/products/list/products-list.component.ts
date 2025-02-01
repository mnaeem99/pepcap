import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IProducts } from '../iproducts';
import { ProductsService } from '../products.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProductsNewComponent } from '../new/products-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { CategoriesService } from 'src/app/entities/categories/categories.service';

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent extends BaseListComponent<IProducts> implements OnInit {

	title = 'Products';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public productsService: ProductsService,
		public errorService: ErrorService,
		public categoriesService: CategoriesService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, productsService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Products';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['id', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
			{
				column: [
            {
					  	key: 'categoryId',
					  	value: undefined,
					  	referencedkey: 'id'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'categoriesDescriptiveField',
				referencedDescriptiveField: 'name',
				service: this.categoriesService,
				associatedObj: undefined,
				table: 'categories',
				type: 'ManyToOne',
				url: 'products',
				listColumn: 'categories',
				label: 'categories',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'updatedAt',
				searchColumn: 'updatedAt',
				label: 'updated At',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
    		{
				column: 'stock',
				searchColumn: 'stock',
				label: 'stock',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'price',
				searchColumn: 'price',
				label: 'price',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'name',
				searchColumn: 'name',
				label: 'name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'id',
				searchColumn: 'id',
				label: 'id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'description',
				searchColumn: 'description',
				label: 'description',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'createdAt',
				searchColumn: 'createdAt',
				label: 'created At',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
			{
	  			column: 'categoriesDescriptiveField',
				searchColumn: 'categories',
				label: 'categories',
				sort: true,
				filter: true,
				type: ListColumnType.String
	  		},
		  	{
				column: 'actions',
				label: 'Actions',
				sort: false,
				filter: false,
				type: ListColumnType.String
			}
		];
		this.selectedColumns = this.columns;
		this.displayedColumns = this.columns.map((obj) => { return obj.column });
  	}
  addNew(comp: any) {
	if(!comp){
		comp = ProductsNewComponent;
	}
	super.addNew(comp);
  }
  
}
