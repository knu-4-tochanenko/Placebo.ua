import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DrugService} from 'src/app/service/drug.service';
import {MatTableDataSource} from '@angular/material/table';


@Component({
  selector: 'app-druginfo',
  templateUrl: './druginfo.component.html',
  styleUrls: ['./druginfo.component.css']
})
export class DruginfoComponent implements OnInit {
  displayedColumns: string[] = ['name', 'type', 'description', 'price', 'storeUrl'];
  dataSource: any[] = null;
  image = '';
  name: string;
  store: string;
  description: string;
  type: string;
  price: string;

  constructor(private route: ActivatedRoute, private drugService: DrugService, private router: Router) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.drugService.getDrugById(params['id']).subscribe(
        res => {
          this.dataSource = new Array(res);
          this.image = res['imageUrl'];
          this.name = res['name'];
          this.store = res['storeUrl'];
          this.description = res['description'];
          this.type = res['type'];
          this.price = (Math.round(Number.parseFloat(res['price']) * 100) / 100).toFixed(2);
        }
      );
    });

  }

}
