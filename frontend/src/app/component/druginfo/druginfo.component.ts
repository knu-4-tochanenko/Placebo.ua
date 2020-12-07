import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DrugService } from 'src/app/service/drug.service';


@Component({
  selector: 'app-druginfo',
  templateUrl: './druginfo.component.html',
  styleUrls: ['./druginfo.component.css']
})



export class DruginfoComponent implements OnInit {
  displayedColumns: string[] = ['name', 'type', 'description', 'price', 'storeUrl', 'imageUrl'];
  dataSource = null;

  constructor(private route: ActivatedRoute, private drugService: DrugService, private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.drugService.getById(params['id']).subscribe(
        res => {
          this.dataSource = {
            name: res['name'],
            imageUrl: res['imageUrl'],
            storeUrl: res['storeUrl'],
            price: res['price'],
            description: res['description'],
            type: res['type']
          }
        }
      )
    });

  }

}
