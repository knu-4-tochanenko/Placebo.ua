import { Component, OnInit } from '@angular/core';

export interface PeriodicElement {
  name: string;
  type:string;
  description: string;
  price: number;
  storeUrl:string;
  imageUrl:string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  { name: 'Ебеве', type: 'Антибіотик', description: '5-ФТОРУРАЦИЛ "ЭБЕВЕ" (5-FLUOROURACIL "EBEWE")', price: 87.99, storeUrl: '#href', imageUrl:'#href'},

];

@Component({
  selector: 'app-druginfo',
  templateUrl: './druginfo.component.html',
  styleUrls: ['./druginfo.component.css']
})



export class DruginfoComponent implements OnInit {
  displayedColumns: string[] = [ 'name', 'type', 'description', 'price', 'storeUrl', 'imageUrl'];
  dataSource = ELEMENT_DATA;
  constructor() { }

  ngOnInit(): void {
  }

}
