import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';
import React from 'react';
import color from 'color';

import RoutedPageComponent from '../../lib/react/components/page/RoutedPageComponent';

class PublicContactPageComponent extends RoutedPageComponent {
  render() {
    return <div className="PublicContactPageComponent">
      <Card className="Card">
        <CardTitle
          title="Contact"
        />
        <CardText>
          Needs more Company Details.
        </CardText>
      </Card>

      <Card className="Card">
        <CardTitle
          title="Map"
        />
        <CardText>
          Needs more Maps.
        </CardText>
      </Card>

      <Card className="Card">
        <CardTitle
          title="Inquiry"
        />
        <CardText>
          Needs more Contact Forms.
        </CardText>
      </Card>
    </div>;
  }
}

PublicContactPageComponent.displayName = 'PublicContactPageComponent';

export default PublicContactPageComponent;
