import React from 'react';
import { Grid, Col, Row } from 'react-bootstrap';

class BreadcrumbsComponent extends React.Component {
  render() {
    return (
      <Grid className="BreadcrumbsComponent">
        <Row>
          <Col xs={12}>
            <ol className="breadcrumb">
              <li><a href="#/">Home</a></li>
              <li><a href="#/cms">CMS</a></li>
              <li><a href="#/cms/users">Users</a></li>
              <li><a href="#/cms/5606e033c0b30f575aaa7d0d">5606e033c0b30f575aaa7d0d</a></li>
              <li className="active">Edit</li>
            </ol>
          </Col>
        </Row>
      </Grid>
    );
  }
}

BreadcrumbsComponent.displayName = 'BreadcrumbsComponent';

export default BreadcrumbsComponent;
