import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatTooltipModule} from '@angular/material/tooltip';
import {CatalogueView, QueryService} from '../../services/query.service';

@Component({
  selector: 'app-catalogue',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatButtonModule, MatIconModule, MatCardModule, MatTooltipModule],
  templateUrl: './catalogue.component.html',
  styleUrls: ['./catalogue.component.css']
})
export class CatalogueComponent implements OnInit {
  private queryService = inject(QueryService);

  displayedColumns: string[] = ['isbn', 'titre', 'auteur', 'nombreExemplaires'];
  dataSource: CatalogueView[] = [];

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.queryService.getCatalogue().subscribe(data => {
      this.dataSource = data;
    });
  }
}
