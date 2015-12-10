import Card from 'material-ui/lib/card/card';
import CardText from 'material-ui/lib/card/card-text';
import CardTitle from 'material-ui/lib/card/card-title';
import React from 'react';

import RoutedPageComponent from '../../lib/react/components/page/RoutedPageComponent';

class PublicAboutPageComponent extends RoutedPageComponent {
  render() {
    return <Card>
      <CardTitle
        title="About"
        subtitle="(hard)Core Business Values"
      />
      <CardText>
        Things.
      </CardText>
    </Card>;
  }
}

PublicAboutPageComponent.displayName = 'PublicAboutPageComponent';

export default PublicAboutPageComponent;
