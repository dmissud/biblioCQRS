import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatIconModule} from '@angular/material/icon';
import {CommandService} from '../../services/command.service';
import {concatMap, delay, from} from 'rxjs';

@Component({
  selector: 'app-test-scenario',
  standalone: true,
  imports: [CommonModule, MatButtonModule, MatCardModule, MatProgressBarModule, MatIconModule],
  templateUrl: './test-scenario.component.html',
  styleUrls: ['./test-scenario.component.css']
})
export class TestScenarioComponent {
  private commandService = inject(CommandService);

  isRunning = false;
  logs: string[] = [];

  runScenario() {
    this.isRunning = true;
    this.logs = ['Démarrage du scénario d\'injection massive...'];

    const actions = [];

    // 1. Référencer 5 ouvrages
    for (let i = 1; i <= 5; i++) {
      const isbn = `ISBN-TEST-${Date.now()}-${i}`;
      actions.push(() => {
        this.logs.push(`Envoi commande: Référencement ouvrage ${isbn}`);
        return this.commandService.referencerOuvrage({ isbn, titre: `Livre Test ${i}`, auteur: 'Bot' });
      });

      // 2. Ajouter 3 exemplaires pour chaque ouvrage
      for (let j = 1; j <= 3; j++) {
        actions.push(() => {
          const codeBarre = `CB-${Date.now()}-${i}-${j}`;
          this.logs.push(`Envoi commande: Ajout exemplaire ${j} pour ${isbn} (Code: ${codeBarre})`);
          return this.commandService.ajouterExemplaire(isbn, {
            codeBarre: codeBarre,
            salle: 'A',
            etagere: '1',
            position: `${j}`
          });
        });
      }
    }

    // Exécution séquentielle avec léger délai pour visualiser
    from(actions).pipe(
      concatMap(action => action().pipe(delay(200)))
    ).subscribe({
      next: () => {},
      complete: () => {
        this.logs.push('Scénario d\'injection terminé avec succès !');
        this.isRunning = false;
      },
      error: (err) => {
        this.logs.push('Erreur lors du scénario: ' + err.message);
        this.isRunning = false;
      }
    });
  }
}
