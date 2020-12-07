import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import {Subscription} from "rxjs";
import { DrugService } from 'src/app/service/drug.service';
import {ActivatedRoute, Router} from "@angular/router";

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

  searchForm: FormGroup;
  submitted = false;

  page = 0;
  size = 10;

  private subscription: Subscription;
  private typesSub: Subscription;

  drugs: Drugs[] = [
    {value: 'Ампулы', viewValue: 'Ампулы'},
    {value: 'Блистер', viewValue: 'Блистер'},
    {value: 'Драже', viewValue: 'Драже'},
    {value: 'Капли', viewValue: 'Капли'},
    {value: 'Капсулы', viewValue: 'Капсулы'},
    {value: 'Карандаш', viewValue: 'Карандаш'},
    {value: 'Пакет', viewValue: 'Пакет'},
    {value: 'Пачка', viewValue: 'Пачка'},
    {value: 'Раствор', viewValue: 'Раствор'},
    {value: 'Саше', viewValue: 'Саше'},
    {value: 'Свечи', viewValue: 'Свечи'},
    {value: 'Стрип', viewValue: 'Стрип'},
    {value: 'Субстанції', viewValue: 'Субстанції'},
    {value: 'Таблетки', viewValue: 'Таблетки'},
    {value: 'Фильтр-пакет', viewValue: 'Фильтр-пакет'},
    {value: 'Флакон', viewValue: 'Флакон'}
  ];

  drugsList: Array<any> = [];

  constructor(private route: ActivatedRoute, private drugService: DrugService, private router: Router) {
    this.typesSub = this.drugService.getTypes().subscribe(

    )
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['page'] != null) {
        this.page = params['page'];
      }
      if (params['size'] != null) {
        this.size = params['size'];
      }
    });

  }

  get f() {
    return this.searchForm.controls;
  }

  onSubmit(): void {
    this.submitted = true;
    
    const searchResponse = {
      name: this.f.name_input.value,
      type: this.f.category_select.value,
      minPrice: this.f.min_price_input.value,
      maxPrice: this.f.max_price_input.value
    }

    this.subscription = this.drugService.searchDrugs(searchResponse).subscribe(
      res => {
        var results = res['content'];
        this.drugsList = [];
        results.array.forEach(element => {
          this.drugsList.push(element);
        });
      },
      err => console.log(err)
    );
  }

  goTo(drugId) {
    this.router.navigateByUrl('/drugInfo?id=' + drugId);
  }

  onOnDestroy() {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
