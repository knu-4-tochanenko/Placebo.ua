import { Component, OnInit } from '@angular/core';

interface Drugs {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-drugs',
  templateUrl: './drugs.component.html',
  styleUrls: ['./drugs.component.css']
})


export class DrugsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  drugs: Drugs[] = [
    {value: 'antibiotics', viewValue: 'Антибіотики'},
    {value: 'gomeophaty', viewValue: 'Гомеопатія'},
    {value: 'vitamins', viewValue: 'Вітаміни'},
    {value: 'adds', viewValue: 'Добавки'}
  ];

}
