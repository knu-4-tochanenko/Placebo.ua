/* tslint:disable:typedef */
import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DrugService} from '../../service/drug.service';
import {Subscription} from 'rxjs';


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
  pages = 1;

  private subscription: Subscription;

  drugs: Drugs[] = [];

  drugsList: Array<any> = [];

  constructor(
    private route: ActivatedRoute,
    private drugService: DrugService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {  }

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      name_input: [null],
      category_select: [null],
      min_price_input: [null],
      max_price_input: [null]
    });

    this.subscription = this.drugService.getTypes().subscribe(
      res => {
        res.forEach(element => this.drugs.push({value: element, viewValue: element}));
      }
    );
    this.route.queryParams.subscribe(params => {
      if (params['page'] != null) {
        this.page = params['page'];
      }
      if (params['size'] != null) {
        this.size = params['size'];
      }
    });

    this.subscription = this.drugService.getDrugs({}, this.page).subscribe(
      res => {
        const results = res['content'];
        this.drugsList = [];
        results.forEach(element => {
          this.drugsList.push(element);
        });
        this.pages = res['totalPages'];
      },
      err => console.log(err)
    );

  }

  get f() {
    return this.searchForm.controls;
  }

  onSubmit(): void {
    const searchResponse = {
      name: this.f.name_input.value,
      type: this.f.category_select.value,
      minPrice: this.f.min_price_input.value,
      maxPrice: this.f.max_price_input.value
    };

    this.subscription = this.drugService.getDrugs(searchResponse, 0).subscribe(
      res => {
        const results = res['content'];
        this.drugsList = [];
        results.forEach(element => {
          this.drugsList.push(element);
        });
        this.pages = res['totalPages'];
        this.page = 0;
      },
      err => console.log(err)
    );
  }

  goTo(drugId) {
    this.router.navigateByUrl('/druginfo?id=' + drugId);
  }

  goToPage(page: number) {
    if (page < 0 || page > this.pages) {
      return;
    }
    this.page = page;
    const searchResponse = {
      name: this.f.name_input.value,
      type: this.f.category_select.value,
      minPrice: this.f.min_price_input.value,
      maxPrice: this.f.max_price_input.value
    };

    this.subscription = this.drugService.getDrugs(searchResponse, page).subscribe(
      res => {
        const results = res['content'];
        this.drugsList = [];
        results.forEach(element => {
          this.drugsList.push(element);
        });
        this.pages = res['totalPages'];
      },
      err => console.log(err)
    );
  }

  prevPage(page: number): void {
    this.goToPage(page - 1);
  }

  nextPage(page: number): void {
    this.goToPage(page + 1);
  }

  onOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

}
